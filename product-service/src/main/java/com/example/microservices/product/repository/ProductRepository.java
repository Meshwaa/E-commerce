package com.example.microservices.product.repository;

import com.example.microservices.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("productRepository")
public interface ProductRepository extends MongoRepository<Product,String> {
}
