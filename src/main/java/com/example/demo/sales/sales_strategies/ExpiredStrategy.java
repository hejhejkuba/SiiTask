package com.example.demo.sales.sales_strategies;

import com.example.demo.product.ProductModel;
import com.example.demo.promocode.PromoCodeModel;

import java.time.LocalDate;

public class ExpiredStrategy implements SalesStrategy {
    ProductModel productModel;
    PromoCodeModel promoCodeModel;
    StringBuilder message;
    Double price;
    public ExpiredStrategy(ProductModel productModel, PromoCodeModel promoCodeModel, StringBuilder message, Double price) {
        this.productModel = productModel;
        this.promoCodeModel = promoCodeModel;
        this.message = message;
        this.price = price;
    }

    @Override
    public boolean ifApplicable() {
        return promoCodeModel.getExpirationDate().isBefore(LocalDate.now());
    }

    @Override
    public void doStrategy() {
        message.append(price = productModel.getRegularPrice()).append(" [code is expired] ");

    }
}
