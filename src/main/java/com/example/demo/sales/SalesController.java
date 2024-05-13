package com.example.demo.sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    @Autowired
    SalesService salesService;

    @GetMapping("/all")
    public ResponseEntity<List<SalesModel>> getAllSales() {
        List<SalesModel> sales = salesService.getAllSales();
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    @GetMapping("/calculate/{product}/{code}")
    public ResponseEntity<String> getDiscountValue(@PathVariable String product, @PathVariable String code) {
        return salesService.getDiscountValue(product, code);
    }

    @GetMapping("/buy/{product}/{code}")
    public ResponseEntity<String> buyItem(@PathVariable String product, @PathVariable String code) {
        return salesService.buyItem(product, code);
    }

    @GetMapping("/export")
    public ResponseEntity<List<SalesExportModel>> getSalesReport() {
        return salesService.generateSalesReport();
    }
}
