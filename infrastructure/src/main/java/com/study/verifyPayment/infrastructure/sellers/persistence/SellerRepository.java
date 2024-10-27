package com.study.verifyPayment.infrastructure.sellers.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<SellerJpaEntity, String> {
}
