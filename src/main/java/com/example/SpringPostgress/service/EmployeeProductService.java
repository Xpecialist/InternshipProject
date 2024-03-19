package com.example.SpringPostgress.service;

import com.example.SpringPostgress.DTO.EmployeeDTO;
import com.example.SpringPostgress.DTO.EmployeeProductDTO;
import com.example.SpringPostgress.DTO.ProductDTO;
import com.example.SpringPostgress.model.Employee;
import com.example.SpringPostgress.model.EmployeeProduct;
import com.example.SpringPostgress.repository.EmployeeProductRepository;
import com.example.SpringPostgress.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
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

    public List<EmployeeProductDTO> getAllEmployeeProducts(){
        List<EmployeeProduct> employeeProductList = employeeProductRepository.findAll();
        return modelMapper.map(employeeProductList, new TypeToken<List<EmployeeProductDTO>>(){}.getType());
    }

    public boolean deleteEmployeeProduct(EmployeeProductDTO employeeProductDTO){
        employeeProductRepository.delete(modelMapper.map(employeeProductDTO, EmployeeProduct.class));
        return true;
    }

    public EmployeeProductDTO getEmployeeProductById(Long id) {
        Optional<EmployeeProduct> employeeProduct = employeeProductRepository.findById(id);
        return modelMapper.map(employeeProduct, EmployeeProductDTO.class);
    }

    public Map<String, List<ProductDTO>> getEmployeeProducts(int companyId) {

        Map<String, List<ProductDTO>> employeeProductMap = new HashMap<>();
        List<EmployeeProductDTO> products = getAllEmployeeProducts();
        List<Employee> companyEmployees = employeeService.getEmployeesByCompanyId(companyId);


        for (Employee ce: companyEmployees){

            String employeeFullName = ce.getFirstName() + " " + ce.getLastName();
            for(EmployeeProductDTO p : products){
                if(ce.getId() == p.getEmployee().getId()){

                    if (!employeeProductMap.containsKey(employeeFullName)) {

                        employeeProductMap.put(employeeFullName, new ArrayList<>());
                    }
                    employeeProductMap.get(employeeFullName).add(p.getProduct());
                }
            }
        }

        /*  SOLUTION WITH QUERY ON REPOSITORY
        List<Object[]> employeeProducts = employeeRepository.findEmployeeProductsByCompanyId(companyId);

        for (Object[] result : employeeProducts) {
            String employeeFullName = (String) result[0] + " " + result[1] ;

            ProductDTO product = new ProductDTO();
            product.setName((String) result[2]);
            product.setDescription((String) result[3]);
            product.setBarcode((String) result[4]);
            product.setId((Integer) result[5]);


            if (!employeeProductMap.containsKey(employeeFullName)) {
                employeeProductMap.put(employeeFullName, new ArrayList<>());
            }
            employeeProductMap.get(employeeFullName).add(product);
        }
        */
        return employeeProductMap;
    }
}