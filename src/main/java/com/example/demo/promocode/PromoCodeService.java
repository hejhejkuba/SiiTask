package com.example.demo.promocode;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PromoCodeService {
    @Autowired
    private PromoCodeRepository promoCodeRepository;

    public PromoCodeModel createPromoCode(PromoCodeModel promoCodeModel) {
        return promoCodeRepository.save(promoCodeModel);
    }

    public List<PromoCodeModel> getAllPromoCodes() {
        return promoCodeRepository.findAll();
    }

    public PromoCodeModel updatePromoCode(Long productId, PromoCodeModel updatedPromoCodeModel) {
        Optional<PromoCodeModel> promoCodeModelOptional = promoCodeRepository.findById(productId);
        if (promoCodeModelOptional.isPresent()) {
            PromoCodeModel existingPromoCode = promoCodeModelOptional.get();
            existingPromoCode.setCode(updatedPromoCodeModel.getCode());
            existingPromoCode.setExpirationDate(updatedPromoCodeModel.getExpirationDate());
            existingPromoCode.setCurrency(updatedPromoCodeModel.getCurrency());
            existingPromoCode.setMaxUsages(updatedPromoCodeModel.getMaxUsages());
            existingPromoCode.setDiscountAmount(updatedPromoCodeModel.getDiscountAmount());
            return promoCodeRepository.save(existingPromoCode);
        } else {
            throw new RuntimeException("Product not found with id: " + productId);
        }
    }

    public PromoCodeModel getPromoCodeById(Long id) {
        PromoCodeModel promoCodeModel = promoCodeRepository.findById(id).orElse(null);
        if (promoCodeModel != null) {
            promoCodeModel.setExpired(!promoCodeModel.getExpirationDate().isAfter(LocalDate.now()));
        }
        return promoCodeModel;
    }

    public PromoCodeModel getPromoCodeByCode(String code) {
        PromoCodeModel promoCodeModel = promoCodeRepository.findByCode(code);
        if (promoCodeModel != null) {
            promoCodeModel.setExpired(!promoCodeModel.getExpirationDate().isAfter(LocalDate.now()));
        }
        return promoCodeModel;
    }
}
