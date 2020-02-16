package com.microservice.sellers_service.repository;

import com.microservice.sellers_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
