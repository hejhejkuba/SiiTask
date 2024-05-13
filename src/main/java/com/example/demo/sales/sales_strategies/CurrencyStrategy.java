package com.example.demo.sales.sales_strategies;

import com.example.demo.product.ProductModel;
import com.example.demo.promocode.PromoCodeModel;

public class CurrencyStrategy implements SalesStrategy {
    ProductModel productModel;
    PromoCodeModel promoCodeModel;
    StringBuilder message;

    Double price;

    public Double getPrice() {
        return price;
    }

    public CurrencyStrategy(ProductModel productModel, PromoCodeModel promoCodeModel, StringBuilder message, Double price) {
        this.productModel = productModel;
        this.promoCodeModel = promoCodeModel;
        this.message = message;
        this.price = price;
    }


    @Override
    public boolean ifApplicable() {
        return !productModel.getCurrency().equals(promoCodeModel.getCurrency());
    }

    @Override
    public void doStrategy() {
        message.append(price = productModel.getRegularPrice()).append(" [currency doesn't match]");
        ;
    }
}
