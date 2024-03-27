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

import lombok.extern.log4j.Log4j2;
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
/**
 * Service class for managing vacation request operations.
 */
@Service
@Log4j2
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

    /**
     * Retrieves a vacation request by its ID.
     *
     * @param id The ID of the vacation request to retrieve.
     * @return The vacation request DTO.
     * @throws ResourceNotFoundException if the vacation request with the given ID is not found.
     */
    public VacationRequestDTO getVacationRequestById(Long id) {
        VacationRequest vacationRequest = vacationRequestRepository.findById(id)
                .orElseThrow(()->  new ResourceNotFoundException("Vacation Request with ID: " +id+" not found."));
        return modelMapper.map(vacationRequest, VacationRequestDTO.class);
    }

    /**
     * Checks and processes a vacation request.
     *
     * @param startDate   The start date of the vacation.
     * @param endDate     The end date of the vacation.
     * @param holiday     The number of holidays during the vacation.
     * @param employeeId  The ID of the employee making the vacation request.
     * @return The processed vacation request DTO.
     */
    public VacationRequestDTO checkVacationRequest(LocalDate startDate, LocalDate endDate, int holiday,long employeeId ) {

        int vacDays = (int) (ChronoUnit.DAYS.between(startDate, endDate) + 1 - holiday);

        EmployeeDTO employee = employeeService.getEmployeeById(employeeId);

        VacationRequestDTO vacationRequestDTO = new VacationRequestDTO();
        vacationRequestDTO.setEmployee(employee);
        vacationRequestDTO.setDays(vacDays);
        vacationRequestDTO.setStartDate(startDate);
        vacationRequestDTO.setEndDate(endDate);

        if (vacDays <= employee.getVacationDays()) {

            log.debug("In loop for checking vacation request");
            vacationRequestDTO.setStatus(VacationStatus.PENDING);
            vacationRequestRepository.save(modelMapper.map(vacationRequestDTO, VacationRequest.class));
        }
        else{ vacationRequestDTO.setStatus(VacationStatus.REJECTED); }

        return vacationRequestDTO;

    }

    /**
     *  Method used by a Map that approves a vacation request in method processVacationRequest().
     *
     * @param request The vacation request to approve.
     * @return The approved vacation request DTO.
     * @throws VacationDaysException if there are not enough vacation days available.
     */
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
    /**
     * Method used by a Map that rejects a vacation request in method processVacationRequest().
     *
     * @param request The vacation request to reject.
     * @return The rejected vacation request DTO.
     */
    public VacationRequestDTO rejectRequest(VacationRequestDTO request){
        request.setStatus(VacationStatus.REJECTED);
        return request;
    }

    /**
     * Processes a vacation request based on the provided status. Uses a Map to accept of reject the request.
     *
     * @param id The ID of the vacation request to approve or reject.
     * @param statusFE  The status we get from the Frontend to set for the vacation request.
     * @return The updated vacation request DTO.
     * @throws VacationDaysException if the vacation request is not pending.
     */
    public VacationRequestDTO processVacationRequest(long id, String statusFE) {

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
