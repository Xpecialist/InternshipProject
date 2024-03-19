package com.example.SpringPostgress.controller;

import com.example.SpringPostgress.DTO.PendingRequest;
import com.example.SpringPostgress.model.VacationRequest;
import com.example.SpringPostgress.service.VacationRequestService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.example.SpringPostgress.DTO.VacationRequestDTO;


import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/vacation-requests")
public class  VacationRequestController {
    
    @Autowired
    private VacationRequestService vacationRequestService;

    @GetMapping
    public List<VacationRequestDTO> getVacationRequests(){
        return vacationRequestService.getAllVacationRequests();
    }


    // create vacationRequest rest api
    @PostMapping
    public VacationRequestDTO saveVacationRequest(@RequestBody VacationRequestDTO vacationRequestDTO) {
        return vacationRequestService.saveVacationRequest(vacationRequestDTO);
    }

    // update vacationRequest rest api
    @PutMapping
    public VacationRequestDTO updateVacationRequest(@RequestBody VacationRequestDTO vacationRequestDTO){
        return vacationRequestService.updateVacationRequest(vacationRequestDTO);
    }

    @PostMapping("/request")
    public VacationRequestDTO checkVacationRequest(@RequestBody PendingRequest vacationRequest){
        return vacationRequestService.checkVacationRequest(vacationRequest.getStartDate(), vacationRequest.getEndDate(), vacationRequest.getHolidays(), vacationRequest.getEmployeeId());
    }

    @GetMapping("/{id}")
    public VacationRequestDTO getVacationRequestById(@PathVariable Long id){
        return vacationRequestService.getVacationRequestById(id);
    }

    @PutMapping("/approval")
    public VacationRequest approveVacationRequest(@RequestBody PendingRequest requestApproval){
        return vacationRequestService.approveVacationRequest(requestApproval.getId(),requestApproval.getStatus().name());
    }

    // delete vacationRequest rest api
    @DeleteMapping
    public boolean deleteVacationRequest(@RequestBody VacationRequestDTO vacationRequestDTO){
        return vacationRequestService.deleteVacationRequest(vacationRequestDTO);
    }

    
}
