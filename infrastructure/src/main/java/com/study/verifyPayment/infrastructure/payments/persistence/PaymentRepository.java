package com.study.verifyPayment.infrastructure.payments.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentJpaEntity, String> {
}
