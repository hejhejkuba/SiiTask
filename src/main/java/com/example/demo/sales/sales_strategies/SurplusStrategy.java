package com.example.demo.sales.sales_strategies;

import com.example.demo.product.ProductModel;
import com.example.demo.promocode.PromoCodeModel;

public class SurplusStrategy implements SalesStrategy {
    ProductModel productModel;
    PromoCodeModel promoCodeModel;
    StringBuilder message;
    Double price;
    public SurplusStrategy(ProductModel productModel, PromoCodeModel promoCodeModel, StringBuilder message, Double price) {
        this.productModel = productModel;
        this.promoCodeModel = promoCodeModel;
        this.message = message;
        this.price = price;
    }

    @Override
    public boolean ifApplicable() {
        return productModel.getRegularPrice() - promoCodeModel.getDiscountAmount() <= 0;
    }

    @Override
    public void doStrategy() {
        message.append(price = 0.0d);

    }
}
