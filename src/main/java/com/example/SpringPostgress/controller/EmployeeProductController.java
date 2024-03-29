package com.example.SpringPostgress.controller;

import com.example.SpringPostgress.DTO.ProductDTO;
import com.example.SpringPostgress.service.EmployeeProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringPostgress.DTO.EmployeeProductDTO;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/employee-products")
public class EmployeeProductController {

    @Autowired
    private EmployeeProductService employeeProductService;

    @GetMapping
    public List<EmployeeProductDTO> getEmployeeEmployeeProducts() {
        return employeeProductService.getAllEmployeeProducts();
    }

    @PostMapping
    public EmployeeProductDTO saveEmployeeProduct(@RequestBody EmployeeProductDTO employeeProductDTO) {
        return employeeProductService.saveEmployeeProduct(employeeProductDTO);
    }

    @PutMapping
    public EmployeeProductDTO updateEmployeeProduct(@RequestBody EmployeeProductDTO employeeProductDTO) {
        return employeeProductService.updateEmployeeProduct(employeeProductDTO);
    }

    @GetMapping("/{id}")
    public EmployeeProductDTO getEmployeeProductById(@PathVariable Long id){
        return employeeProductService.getEmployeeProductById(id);
    }

    @DeleteMapping
    public boolean deleteEmployeeProduct(@RequestBody EmployeeProductDTO employeeProductDTO) {
        return employeeProductService.deleteEmployeeProduct(employeeProductDTO);
    }

    @GetMapping("/company-products/{companyId}")
    public Map<String, List<ProductDTO>> getEmployeeProducts(@PathVariable int companyId){
        return employeeProductService.getEmployeeProducts(companyId);
    }

}
