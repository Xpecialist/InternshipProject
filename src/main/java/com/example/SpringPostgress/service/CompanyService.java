package com.example.SpringPostgress.service;

import com.example.SpringPostgress.DTO.CompanyDTO;
import com.example.SpringPostgress.exception.ResourceNotFoundException;
import com.example.SpringPostgress.model.*;
import com.example.SpringPostgress.repository.CompanyRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    EmployeeService employeeService;



    public CompanyDTO saveCompany(CompanyDTO companyDTO){
        companyRepository.save(modelMapper.map(companyDTO, Company.class));
        return companyDTO;
    }

    public CompanyDTO updateCompany(CompanyDTO companyDTO){
        companyRepository.save(modelMapper.map(companyDTO, Company.class));
        return companyDTO;
    }

    public List<CompanyDTO> getAllCompanies(){
        List<Company> companyList = companyRepository.findAll();
        if (companyList.isEmpty()) {
            throw new ResourceNotFoundException("No companies found.");
        }
        return modelMapper.map(companyList, new TypeToken<List<CompanyDTO>>(){}.getType());
    }

    public boolean deleteCompany(CompanyDTO companyDTO){
        companyRepository.delete(modelMapper.map(companyDTO, Company.class));
        return true;
    }

    public CompanyDTO getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(()->  new ResourceNotFoundException("Company with ID: " +id+" not found."));
        return modelMapper.map(company, CompanyDTO.class);
    }

    public double getCompanyExpenses(int companyId) {

        List<Employee> employees = employeeService.getEmployeesByCompanyId(companyId);
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("No company employees found.");
        }

        return employees.stream().mapToDouble(Employee::getSalary).sum();
    }


}

