package com.example.demo.promocode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCodeModel, Long> {
    PromoCodeModel findByCode(String code);
}
