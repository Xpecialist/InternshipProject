package com.example.SpringPostgress.service;

import com.example.SpringPostgress.DTO.ProductDTO;
import com.example.SpringPostgress.exception.ResourceNotFoundException;
import com.example.SpringPostgress.model.Product;
import com.example.SpringPostgress.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProductDTO saveProduct(ProductDTO productDTO){
        productRepository.save(modelMapper.map(productDTO, Product.class));
        return productDTO;
    }

    public ProductDTO updateProduct(ProductDTO productDTO){
        productRepository.save(modelMapper.map(productDTO, Product.class));
        return productDTO;
    }

    public List<ProductDTO> getAllProducts(){
        List<Product> productList = productRepository.findAll();
        if (productList.isEmpty()) {
            throw new ResourceNotFoundException("No products found.");
        }
        return modelMapper.map(productList, new TypeToken<List<ProductDTO>>(){}.getType());
    }

    public boolean deleteProduct(ProductDTO productDTO){
        productRepository.delete(modelMapper.map(productDTO, Product.class));
        return true;
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->  new ResourceNotFoundException("Product with ID: " +id+" not found."));
        return modelMapper.map(product, ProductDTO.class);
    }
}
