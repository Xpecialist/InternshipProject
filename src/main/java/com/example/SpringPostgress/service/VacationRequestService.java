package com.example.SpringPostgress.service;

import com.example.SpringPostgress.DTO.EmployeeDTO;
import com.example.SpringPostgress.DTO.VacationRequestDTO;
import com.example.SpringPostgress.Enum.VacationStatus;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        return modelMapper.map(vacationRequestList, new TypeToken<List<VacationRequestDTO>>() {
        }.getType());
    }

    public boolean deleteVacationRequest(VacationRequestDTO vacationRequestDTO) {
        vacationRequestRepository.delete(modelMapper.map(vacationRequestDTO, VacationRequest.class));
        return true;

    }

    public VacationRequestDTO getVacationRequestById(Long id) {
        Optional<VacationRequest> vacationRequest = vacationRequestRepository.findById(id);
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

    public VacationRequest approveVacationRequest(long id, String status) {

        VacationRequestDTO request = getVacationRequestById(id);

        if(Objects.equals(status, "accepted")){

            request.setStatus(VacationStatus.APPROVED);

            EmployeeDTO employee = employeeService.getEmployeeById(request.getEmployee().getId());
            employee.setVacationDays(( employee.getVacationDays() - request.getDays()));

            employeeService.updateEmployee(employee);
        }
        else if (Objects.equals(status, "rejected")) {
            request.setStatus(VacationStatus.REJECTED);
        }

        VacationRequestDTO requestDTO = updateVacationRequest(request); //ισως και να μην χρειαζεται

        return modelMapper.map(requestDTO, VacationRequest.class); //μπορει απλα να θελει το request

    }
}