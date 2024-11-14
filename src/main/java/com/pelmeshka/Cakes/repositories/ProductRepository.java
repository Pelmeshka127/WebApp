package com.pelmeshka.Cakes.repositories;

import com.pelmeshka.Cakes.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

}