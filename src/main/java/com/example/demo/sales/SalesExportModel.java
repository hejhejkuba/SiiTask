package com.example.demo.sales;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SalesExportModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String currency;
    private Double totalAmount;
    private Double totalDiscount;
    private Long numOfPurchases;

    public SalesExportModel(String currency, Double totalAmount, Double totalDiscount, Long numOfPurchases) {
        this.currency = currency;
        this.totalAmount = totalAmount;
        this.totalDiscount = totalDiscount;
        this.numOfPurchases = numOfPurchases;
    }
}

