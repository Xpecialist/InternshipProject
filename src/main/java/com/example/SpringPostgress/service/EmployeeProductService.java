package com.example.SpringPostgress.service;

import com.example.SpringPostgress.DTO.EmployeeProductDTO;
import com.example.SpringPostgress.DTO.ProductDTO;
import com.example.SpringPostgress.exception.ResourceNotFoundException;
import com.example.SpringPostgress.model.Employee;
import com.example.SpringPostgress.model.EmployeeProduct;
import com.example.SpringPostgress.repository.EmployeeProductRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
/**
 * Service class for managing employee product operations.
 */
@Service
@Log4j2
@Transactional
public class EmployeeProductService {

    @Autowired
    private EmployeeProductRepository employeeProductRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    EmployeeService employeeService;

    public EmployeeProductDTO saveEmployeeProduct(EmployeeProductDTO employeeProductDTO){
        employeeProductRepository.save(modelMapper.map(employeeProductDTO, EmployeeProduct.class));
        return employeeProductDTO;
    }

    public EmployeeProductDTO updateEmployeeProduct(EmployeeProductDTO employeeProductDTO){
        employeeProductRepository.save(modelMapper.map(employeeProductDTO, EmployeeProduct.class));
        return employeeProductDTO;
    }

    /**
     * Retrieves all employee products.
     *
     * @return A list of all employee product DTOs.
     * @throws ResourceNotFoundException if no employee products are found.
     */
    @Transactional(readOnly = true)
    public List<EmployeeProductDTO> getAllEmployeeProducts(){
        List<EmployeeProduct> employeeProductList = employeeProductRepository.findAll();
        if (employeeProductList.isEmpty()) {
            throw new ResourceNotFoundException("No employee products found.");
        }
        return modelMapper.map(employeeProductList, new TypeToken<List<EmployeeProductDTO>>(){}.getType());
    }

    public boolean deleteEmployeeProduct(EmployeeProductDTO employeeProductDTO){
        employeeProductRepository.delete(modelMapper.map(employeeProductDTO, EmployeeProduct.class));
        return true;
    }

    /**
     * Retrieves an employee product by its ID.
     *
     * @param id The ID of the employee product to retrieve.
     * @return The employee product DTO.
     * @throws ResourceNotFoundException if the employee product with the given ID is not found.
     */
    @Transactional(readOnly = true)
    public EmployeeProductDTO getEmployeeProductById(Long id) {
        EmployeeProduct employeeProduct = employeeProductRepository.findById(id)
                .orElseThrow(()->  new ResourceNotFoundException("Employee Product with ID: " +id+" not found."));
        return modelMapper.map(employeeProduct, EmployeeProductDTO.class);
    }

    /**
     * Retrieves a map of employees and their associated products for a given company.
     *
     * @param companyId The ID of the company.
     * @return A map where the key is the employee name and the value is a list of product DTOs.
     */
    @Transactional(readOnly = true)
    public Map<String, List<ProductDTO>> getEmployeeProducts(int companyId) {

        Map<String, List<ProductDTO>> employeeProductMap = new HashMap<>();
        List<EmployeeProductDTO> products = getAllEmployeeProducts();
        List<Employee> companyEmployees = employeeService.getEmployeesByCompanyId(companyId);
        // Map to store counts of employees with duplicate name
        Map<String, Integer> nameCountMap = new HashMap<>();

        //TODO: NEEDS TO BE BROKEN TO METHODS
        for (Employee ce: companyEmployees){

            StringBuilder employeeFullName = getFullNameList(ce, nameCountMap);

            for(EmployeeProductDTO p : products){
                if(ce.getId() == p.getEmployee().getId()){
                    //Δεν ξερω ποσο σωστο ειναι να χειριζομαι map εκτος του range του method(Μπορει να θελει να αρχικοποιησω αλλο map μεσα και να το map-αρω μετα στον τελικο)
                    fillCompanyEmployeesProductsMap(employeeProductMap, p,employeeFullName);
                }
            }
        }
        return employeeProductMap;
    }

    private StringBuilder getFullNameList(Employee ce, Map<String, Integer> map){

        log.debug("In loop for creating Full Name");
        StringBuilder employeeFullName = new StringBuilder(ce.getFirstName() + " " + ce.getLastName());
        //Adds count(starts with 0) to duplicate name entries on mapCountMap
        int count = map.getOrDefault(employeeFullName.toString(), 0) + 1;
        map.put(employeeFullName.toString(), count);
        //Add a "white space" to map's key for each duplication of itself
        employeeFullName.append(" ".repeat(Math.max(0, count)));
        return employeeFullName;
    }
    private void fillCompanyEmployeesProductsMap(Map<String, List<ProductDTO>> map, EmployeeProductDTO p, StringBuilder employeeFullName){

            log.debug("In loop for filling out the Company Employees Products Map");
            if (!map.containsKey(employeeFullName.toString())) {
                map.put(employeeFullName.toString(), new ArrayList<>());
            }
            map.get(employeeFullName.toString()).add(p.getProduct());
    }
}
