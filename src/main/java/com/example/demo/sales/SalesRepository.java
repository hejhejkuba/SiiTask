package com.example.demo.sales;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<SalesModel, Long> {
}
