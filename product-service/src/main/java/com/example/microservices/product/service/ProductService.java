package com.example.microservices.product.service;

import com.example.microservices.product.dto.ProductRequest;
import com.example.microservices.product.dto.ProductResponse;
import com.example.microservices.product.model.Product;
import com.example.microservices.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .id(productRequest.id())
                .name(productRequest.name())
                .description(productRequest.description())
                .skuCode(productRequest.skuCode())
                .price(productRequest.price())
                .build();
        var savedProduct = productRepository.save(product);
        log.info("Product created successfully!");
        return new ProductResponse(savedProduct.getId(), savedProduct.getName(), savedProduct.getDescription(),savedProduct.getSkuCode(), savedProduct.getPrice());
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(product -> new ProductResponse(
                product.getId(), product.getName(), product.getDescription(), product.getSkuCode(), product.getPrice()
        )).collect(Collectors.toList());
    }

    public void deleteProducts() {
        productRepository.deleteAll();
    }
}
