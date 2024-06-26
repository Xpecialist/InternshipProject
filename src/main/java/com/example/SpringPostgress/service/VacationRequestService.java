package com.example.SpringPostgress.service;

import com.example.SpringPostgress.DTO.EmployeeDTO;
import com.example.SpringPostgress.DTO.VacationRequestDTO;
import com.example.SpringPostgress.Enum.VacationStatus;
import com.example.SpringPostgress.exception.NullExpressionException;
import com.example.SpringPostgress.exception.ResourceNotFoundException;
import com.example.SpringPostgress.exception.VacationDaysException;
import com.example.SpringPostgress.model.VacationRequest;
import com.example.SpringPostgress.repository.VacationRequestRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.Getter;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.DayOfWeek;
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

    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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

        if (startDate == null || endDate == null) {
            throw new NullExpressionException("Either input dates are NULL! ");
        }

        int vacDays = getBusinessDays(startDate, endDate) - holiday; //doesn't need +1 cause getBusinessDays() counts the first date as well
        if(vacDays <= 0){ throw new VacationDaysException("No need for vacation request, vacDays <= 0");}
        EmployeeDTO employee = employeeService.getEmployeeById(employeeId);
        VacationRequestDTO vacationRequestDTO = createVacationRequest(employee, startDate, endDate, vacDays);

        if (vacDays <= employee.getVacationDays()) {
            log.debug("Available vacation days >= requested vacation days.");
            vacationRequestDTO.setStatus(VacationStatus.PENDING);
            vacationRequestRepository.save(modelMapper.map(vacationRequestDTO, VacationRequest.class));
        }
        else{
            log.debug("Available vacation days <= requested vacation days.");
            vacationRequestDTO.setStatus(VacationStatus.REJECTED);
            throw new VacationDaysException("Not enough days available. Remaining vacation days: "+employee.getVacationDays()+", while you asked for: "+vacDays+". Request REJECTED & NOT SAVED.");
        }

        return vacationRequestDTO;

    }

    /**
     * Creates manually a vacationRequestDTO object with the given employee, start date, end date, and number of vacation days.
     *
     * @param employee   the employee associated with the vacation request
     * @param startDate  the start date of the vacation request
     * @param endDate    the end date of the vacation request
     * @param vacDays    the number of vacation days in the request
     * @return a vacation request DTO object representing the vacation request
     */
    private VacationRequestDTO createVacationRequest(EmployeeDTO employee, LocalDate startDate, LocalDate endDate, int vacDays){

        VacationRequestDTO vacationRequestDTO = new VacationRequestDTO();
        vacationRequestDTO.setEmployee(employee);
        vacationRequestDTO.setDays(vacDays);
        vacationRequestDTO.setStartDate(startDate);
        vacationRequestDTO.setEndDate(endDate);
        return vacationRequestDTO;
    }

    /**
     * Calculates the number of business days (excluding weekends) between two dates.
     *
     * @param startDate the start date of the range (inclusive)
     * @param endDate   the end date of the range (inclusive)
     * @return the number of business days between the start and end dates
     */
    private static int getBusinessDays(LocalDate startDate, LocalDate endDate) {
        int businessDays = 0;
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            if (isBusinessDay(date)) { businessDays++; }
            date = date.plusDays(1);
        }
        return businessDays;
    }

    /**
     * Checks if a given date is a business day (not a Saturday or Sunday).
     * @param date the date to check
     * @return {@code true} if the date is a business day, {@code false} otherwise
     */
    private static boolean isBusinessDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    /**
     *  Method used by a Map that approves a vacation request in method processVacationRequest().
     *
     * @param request The vacation request to approve.
     * @return The approved vacation request DTO.
     * @throws VacationDaysException if there are not enough vacation days available.
     */
    private VacationRequestDTO approveRequest(VacationRequestDTO request){

        EmployeeDTO employee = employeeService.getEmployeeById(request.getEmployee().getId());
        int remainingDays = employee.getVacationDays() - request.getDays();
        if ( remainingDays < 0) {
            throw new VacationDaysException("Not enough days available. Remaining  vacation days: "+employee.getVacationDays()+", while you asked for: "+request.getDays());
        }
        else {
            request.setStatus(VacationStatus.APPROVED);
            employee.setVacationDays(remainingDays);
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
    private VacationRequestDTO rejectRequest(VacationRequestDTO request){
        request.setStatus(VacationStatus.REJECTED);
        return request;
    }

    /**
     * Processes a vacation request based on the provided status. Uses a Map to accept of reject the request.
     *
     * @param id The ID of the vacation request to approve or reject.
     * @param statusFe  The status we get from the Frontend to set for the vacation request.
     * @return The updated vacation request DTO.
     * @throws VacationDaysException if the vacation request is not pending.
     */
    public VacationRequestDTO processVacationRequest(long id, String statusFe) {

        VacationRequestDTO request = getVacationRequestById(id);
        if (!request.getStatus().equals(VacationStatus.PENDING)){
            throw new IllegalArgumentException("Vacation Request with ID: "+id+" is not PENDING.");
        }
        return updateVacationRequest(processVacationRequestMap(request, statusFe));
    }

    /**
     * Processes a vacation request based on the provided status and returns the updated request.
     *
     * @param request The VacationRequestDTO object to be processed.
     * @param statusFe The status to which the request should be updated based on.
     * @return The updated VacationRequestDTO object after processing.
     * @throws IllegalArgumentException if the provided status is not valid.
     */
    private VacationRequestDTO processVacationRequestMap(VacationRequestDTO request, String statusFe){

        Map<String, Function<VacationRequestDTO,VacationRequestDTO>> requestMap = new HashMap<>();
        //checking if status is in correct form
        VacationStatus.checkStatusValidity(statusFe);

        requestMap.put("ACCEPTED", this::approveRequest);
        requestMap.put("REJECTED", this::rejectRequest);
        return requestMap.get(statusFe).apply(request);
    }
}
