package com.example.SpringPostgress.service;

import com.example.SpringPostgress.DTO.EmployeeDTO;
import com.example.SpringPostgress.DTO.VacationRequestDTO;
import com.example.SpringPostgress.Enum.VacationStatus;
import com.example.SpringPostgress.exception.ResourceNotFoundException;
import com.example.SpringPostgress.exception.VacationDaysException;
import com.example.SpringPostgress.model.VacationRequest;
import com.example.SpringPostgress.repository.VacationRequestRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;


@Service
@Transactional
public class VacationRequestService {
    @Getter


    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    @Autowired
    private ModelMapper modelMapper;

    public VacationRequestDTO saveVacationRequest(VacationRequestDTO vacationRequestDTO) {
        vacationRequestRepository.save(modelMapper.map(vacationRequestDTO, VacationRequest.class));
        return vacationRequestDTO;
    }

    public VacationRequestDTO updateVacationRequest(VacationRequestDTO vacationRequestDTO) {
        vacationRequestRepository.save(modelMapper.map(vacationRequestDTO, VacationRequest.class));
        return vacationRequestDTO;
    }

    public List<VacationRequestDTO> getAllVacationRequests() {
        List<VacationRequest> vacationRequestList = vacationRequestRepository.findAll();
        if (vacationRequestList.isEmpty()) {
            throw new ResourceNotFoundException("No vacation requests found.");
        }
        return modelMapper.map(vacationRequestList, new TypeToken<List<VacationRequestDTO>>() {
        }.getType());
    }

    public boolean deleteVacationRequest(VacationRequestDTO vacationRequestDTO) {
        vacationRequestRepository.delete(modelMapper.map(vacationRequestDTO, VacationRequest.class));
        return true;

    }

    public VacationRequestDTO getVacationRequestById(Long id) {
        VacationRequest vacationRequest = vacationRequestRepository.findById(id)
                .orElseThrow(()->  new ResourceNotFoundException("Vacation Request with ID: " +id+" not found."));
        return modelMapper.map(vacationRequest, VacationRequestDTO.class);
    }

    public VacationRequestDTO checkVacationRequest(LocalDate startDate, LocalDate endDate, int holiday,long employeeId ) {

        int vacDays = (int) (ChronoUnit.DAYS.between(startDate, endDate) + 1 - holiday);

        EmployeeDTO employee = employeeService.getEmployeeById(employeeId);

        VacationRequestDTO vacationRequestDTO = new VacationRequestDTO();
        vacationRequestDTO.setEmployee(employee);
        vacationRequestDTO.setDays(vacDays);
        vacationRequestDTO.setStartDate(startDate);
        vacationRequestDTO.setEndDate(endDate);

        if (vacDays <= employee.getVacationDays()) {


            vacationRequestDTO.setStatus(VacationStatus.PENDING);
            vacationRequestRepository.save(modelMapper.map(vacationRequestDTO, VacationRequest.class));
        }
        else{ vacationRequestDTO.setStatus(VacationStatus.REJECTED); }

        return vacationRequestDTO;

    }
    public VacationRequestDTO approveRequest(VacationRequestDTO request){


        EmployeeDTO employee = employeeService.getEmployeeById(request.getEmployee().getId());
        int remainingDays = employee.getVacationDays() - request.getDays();
        if ( remainingDays < 0) {
            throw new VacationDaysException("Not enough days available. Remaining days: "+remainingDays);
        }
        else {
            request.setStatus(VacationStatus.APPROVED);
            employee.setVacationDays((employee.getVacationDays() - request.getDays()));
            employeeService.updateEmployee(employee);
            return request;
        }
    }

    public VacationRequestDTO rejectRequest(VacationRequestDTO request){
        request.setStatus(VacationStatus.REJECTED);
        return request;
    }

    public VacationRequestDTO approveVacationRequest(long id, String statusFE) {

        VacationRequestDTO request = getVacationRequestById(id);
        if (Objects.equals(String.valueOf(request.getStatus()), "pending")){
            throw new VacationDaysException("Vacation Request with ID: "+id+" is not pending.");
        }

        Map<String, Function<VacationRequestDTO,VacationRequestDTO>> requestMap = new HashMap<>();
        requestMap.put("accepted", this::approveRequest);
        requestMap.put("rejected", this::rejectRequest);
        VacationRequestDTO r = requestMap.get(statusFE).apply(request);

        return updateVacationRequest(r);

    }
}
