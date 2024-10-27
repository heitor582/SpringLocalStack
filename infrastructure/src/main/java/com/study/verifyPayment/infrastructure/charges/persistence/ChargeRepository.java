package com.study.verifyPayment.infrastructure.charges.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeRepository extends JpaRepository<ChargeJpaEntity, String> {
}
