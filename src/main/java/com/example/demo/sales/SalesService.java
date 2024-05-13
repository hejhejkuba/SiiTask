package com.example.demo.sales;

import com.example.demo.product.ProductModel;
import com.example.demo.product.ProductService;
import com.example.demo.promocode.PromoCodeModel;
import com.example.demo.promocode.PromoCodeService;
import com.example.demo.sales.sales_strategies.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SalesService {
    @Autowired
    SalesRepository salesRepository;
    @Autowired
    ProductService productService;
    @Autowired
    PromoCodeService promoCodeService;
    @PersistenceContext
    private EntityManager entityManager;


    public List<SalesModel> getAllSales() {
        return salesRepository.findAll();
    }

    public ResponseEntity<String> getDiscountValue(String product, String code) {
        String message = "";
        StringBuilder stringBuilder = new StringBuilder(message);
        PromoCodeModel promoCodeModel = promoCodeService.getPromoCodeByCode(code);
        ProductModel productModel = productService.getProductByName(product);
        if (productModel == null || promoCodeModel == null) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
        Double price = productModel.getRegularPrice();
        //price = (productModel.getRegularPrice() - promoCodeModel.getDiscountAmount());
        List<SalesStrategy> strategies = List.of(
                new ExpiredStrategy(productModel, promoCodeModel, stringBuilder, price),
                new CurrencyStrategy(productModel, promoCodeModel, stringBuilder, price),
                new MaxUsageStrategy(productModel, promoCodeModel, stringBuilder, price),
                new SurplusStrategy(productModel, promoCodeModel, stringBuilder, price)

        );
        strategies.stream().filter(salesStrategy -> salesStrategy.ifApplicable()).findFirst().orElse(new DefaultStrategy(productModel, promoCodeModel, stringBuilder, price)).doStrategy();

        return new ResponseEntity<>("PRICE : " + stringBuilder, HttpStatus.OK);
    }

    public ResponseEntity<String> buyItem(String product, String code) {
        String message = "";
        StringBuilder stringBuilder = new StringBuilder(message);

        PromoCodeModel promoCodeModel = promoCodeService.getPromoCodeByCode(code);
        ProductModel productModel = productService.getProductByName(product);
        if (productModel == null || promoCodeModel == null) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
        Double price = productModel.getRegularPrice();
        List<SalesStrategy> strategies = List.of(
                new ExpiredStrategy(productModel, promoCodeModel, stringBuilder, price),
                new CurrencyStrategy(productModel, promoCodeModel, stringBuilder, price),
                new MaxUsageStrategy(productModel, promoCodeModel, stringBuilder, price),
                new SurplusStrategy(productModel, promoCodeModel, stringBuilder, price)

        );
        Optional<SalesStrategy> applicableStrategy = strategies.stream()
                .filter(SalesStrategy::ifApplicable)
                .findFirst();
        if (applicableStrategy.isEmpty()) {
            int remainCodeUsages = promoCodeModel.getMaxUsages() - 1;
            if (remainCodeUsages >= 0) {
                promoCodeModel.setMaxUsages(remainCodeUsages);
                promoCodeService.updatePromoCode(promoCodeModel.getId(), promoCodeModel);
            }
            SalesModel salesModel = new SalesModel();
            salesModel.setPurchasedDate(LocalDate.now());
            salesModel.setRegularPrice(productModel.getRegularPrice());
            salesModel.setDiscount(promoCodeModel.getDiscountAmount());
            salesModel.setProduct(productModel);
            createSales(salesModel);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } else {
            applicableStrategy.get().doStrategy();
        }
        return new ResponseEntity<>(" Error : PRICE " + stringBuilder, HttpStatus.OK);
    }

    public void createSales(SalesModel salesModel) {
        salesRepository.save(salesModel);
    }

    public ResponseEntity<List<SalesExportModel>> generateSalesReport() {
        try {
            Session session = entityManager.unwrap(Session.class);

            String hql = "SELECT new SalesExportModel(p.currency, ROUND(SUM(s.regularPrice), 2), ROUND(SUM(s.discount), 2), COUNT(*)) " +
                    "FROM SalesModel s " +
                    "INNER JOIN s.product p " +
                    "GROUP BY p.currency";

            Query<SalesExportModel> query = session.createQuery(hql, SalesExportModel.class);
            List<SalesExportModel> results = query.getResultStream().toList();

            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
