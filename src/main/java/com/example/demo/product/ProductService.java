package com.example.demo.product;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    @Autowired
    ProductRepository productRepo;

    public ProductModel createProduct(ProductModel product) {
        return productRepo.save(product);
    }

    public List<ProductModel> getAllProducts() {
        List<ProductModel> productModels = new ArrayList<ProductModel>();
        productRepo.findAll().forEach(productModel -> productModels.add(productModel));
        return productModels;
    }

    public ProductModel updateProduct(Long productId, ProductModel updatedProduct) {
        Optional<ProductModel> productOptional = productRepo.findById(productId);
        if (productOptional.isPresent()) {
            ProductModel existingProduct = productOptional.get();
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setRegularPrice(updatedProduct.getRegularPrice());
            existingProduct.setCurrency(updatedProduct.getCurrency());
            return productRepo.save(existingProduct);
        } else {
            throw new RuntimeException("Product not found with id: " + productId);
        }
    }

    public ProductModel getProductByName(String name) {
        return productRepo.findByName(name);
    }
}
