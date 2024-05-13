package com.example.demo.promocode;

import com.example.demo.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promo-codes")
public class PromoCodeController {

    @Autowired
    private PromoCodeService promoCodeService;
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<PromoCodeModel> createPromoCode(@RequestBody PromoCodeModel promoCodeModel) {
        PromoCodeModel createdPromoCode = promoCodeService.createPromoCode(promoCodeModel);
        return new ResponseEntity<>(createdPromoCode, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PromoCodeModel>> getAllPromoCodes() {
        List<PromoCodeModel> promoCodes = promoCodeService.getAllPromoCodes();
        return new ResponseEntity<>(promoCodes, HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<PromoCodeModel> getPromoCodeDetails(@PathVariable Long id) {
        PromoCodeModel promoCode = promoCodeService.getPromoCodeById(id);
        if (promoCode != null) {
            return new ResponseEntity<>(promoCode, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<PromoCodeModel> getPromoCodeByCode(@PathVariable String code) {
        PromoCodeModel promoCode = promoCodeService.getPromoCodeByCode(code);
        if (promoCode != null) {
            return new ResponseEntity<>(promoCode, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
