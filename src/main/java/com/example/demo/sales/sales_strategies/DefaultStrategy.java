package com.example.demo.sales.sales_strategies;

import com.example.demo.product.ProductModel;
import com.example.demo.promocode.PromoCodeModel;

public class DefaultStrategy implements SalesStrategy{
    ProductModel productModel;
    PromoCodeModel promoCodeModel;
    StringBuilder message;
    Double price;
    public DefaultStrategy(ProductModel productModel, PromoCodeModel promoCodeModel, StringBuilder message, Double price) {
        this.productModel = productModel;
        this.promoCodeModel = promoCodeModel;
        this.message = message;
        this.price = price;
    }

    @Override
    public boolean ifApplicable() {
        return true;
    }

    @Override
    public void doStrategy() {
        message.append(price = productModel.getRegularPrice() - promoCodeModel.getDiscountAmount());
    }
}
