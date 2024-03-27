package com.example.SpringPostgress.service;

import com.example.SpringPostgress.DTO.EmployeeDTO;
import com.example.SpringPostgress.exception.ResourceNotFoundException;
import com.example.SpringPostgress.model.Employee;
import com.example.SpringPostgress.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Service class for managing employee operations.
 */
@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO){
        employeeRepository.save(modelMapper.map(employeeDTO, Employee.class));
        return employeeDTO;
    }

    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO){
        employeeRepository.save(modelMapper.map(employeeDTO, Employee.class));
        return employeeDTO;
    }

    /**
     * Retrieves an employee by its ID.
     *
     * @param id The ID of the employee to retrieve.
     * @return The employee DTO.
     * @throws ResourceNotFoundException if the employee with the given ID is not found.
     */
    public EmployeeDTO getEmployeeById(long id) {
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(()->  new ResourceNotFoundException("Employee with ID: " +id+" not found."));
            return modelMapper.map(employee, EmployeeDTO.class);

    }

    /**
     * Retrieves all employees.
     *
     * @return A list of all employee DTOs.
     * @throws ResourceNotFoundException if no employees are found.
     */
    public List<EmployeeDTO> getAllEmployees(){
        List<Employee> employeeList = employeeRepository.findAll();
        if (employeeList.isEmpty()) {
            throw new ResourceNotFoundException("No employees found.");
        }
        return modelMapper.map(employeeList, new TypeToken<List<EmployeeDTO>>(){}.getType());
    }

    public boolean deleteEmployee(EmployeeDTO employeeDTO){
        employeeRepository.delete(modelMapper.map(employeeDTO, Employee.class));
        return true;
    }

    /**
     * Retrieves employees by company ID.
     *
     * @param companyId The ID of the company.
     * @return A list of employees belonging to the company.
     * @throws ResourceNotFoundException if no employees are found for the company.
     */
    public List<Employee> getEmployeesByCompanyId(long companyId){

        List<Employee> companyEmployees = employeeRepository.findByCompanyId(companyId);
        if(companyEmployees.isEmpty()){ throw new ResourceNotFoundException("This company ID: "+companyId+" doesn't have employees");}

        return companyEmployees;

    }
}
