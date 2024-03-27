package com.example.SpringPostgress.service;

import com.example.SpringPostgress.DTO.CompanyDTO;
import com.example.SpringPostgress.exception.ResourceNotFoundException;
import com.example.SpringPostgress.model.*;
import com.example.SpringPostgress.repository.CompanyRepository;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
/**
 * Service class for managing company operations.
 */
@Service
@Transactional
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    EmployeeService employeeService;


    /**
     * Saves a company entity.
     *
     * @param companyDTO The company DTO to save.
     * @return The saved company DTO.
     */
    public CompanyDTO saveCompany(CompanyDTO companyDTO){
        companyRepository.save(modelMapper.map(companyDTO, Company.class));
        return companyDTO;
    }

    /**
     * Updates a company entity.
     *
     * @param companyDTO The company DTO to update.
     * @return The updated company DTO.
     */
    public CompanyDTO updateCompany(CompanyDTO companyDTO){
        companyRepository.save(modelMapper.map(companyDTO, Company.class));
        return companyDTO;
    }

    /**
     * Retrieves all companies.
     *
     * @return A list of all company DTOs.
     * @throws ResourceNotFoundException if no companies are found.
     */
    @Transactional(readOnly = true)
    public List<CompanyDTO> getAllCompanies(){
        List<Company> companyList = companyRepository.findAll();
        if (companyList.isEmpty()) {
            throw new ResourceNotFoundException("No companies found.");
        }
        return modelMapper.map(companyList, new TypeToken<List<CompanyDTO>>(){}.getType());
    }

    /**
     * Deletes a company entity.
     *
     * @param companyDTO The company DTO to delete.
     * @return True if deletion is successful, false otherwise.
     */
    public boolean deleteCompany(CompanyDTO companyDTO){
        companyRepository.delete(modelMapper.map(companyDTO, Company.class));
        return true;
    }

    /**
     * Retrieves a company by its ID.
     *
     * @param id The ID of the company to retrieve.
     * @return The company DTO.
     * @throws ResourceNotFoundException if the company with the given ID is not found.
     */
    @Transactional(readOnly = true)
    public CompanyDTO getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(()->  new ResourceNotFoundException("Company with ID: " +id+" not found."));
        return modelMapper.map(company, CompanyDTO.class);
    }

    /**
     * Calculates the total expenses of a company.
     *
     * @param companyId The ID of the company.
     * @return The total expenses of the company.
     * @throws ResourceNotFoundException if no employees are found for the company.
     */
    @Transactional(readOnly = true)
    public double getCompanyExpenses(int companyId) {

        List<Employee> employees = employeeService.getEmployeesByCompanyId(companyId);
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("No company employees found.");
        }

        return employees.stream().mapToDouble(Employee::getSalary).sum();
    }


}

