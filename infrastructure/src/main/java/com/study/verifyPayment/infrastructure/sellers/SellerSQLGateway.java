package com.study.verifyPayment.infrastructure.sellers;

import com.study.verifyPayment.domain.sellers.Seller;
import com.study.verifyPayment.domain.sellers.SellerGateway;
import com.study.verifyPayment.domain.sellers.SellerID;
import com.study.verifyPayment.infrastructure.sellers.persistence.SellerJpaEntity;
import com.study.verifyPayment.infrastructure.sellers.persistence.SellerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SellerSQLGateway implements SellerGateway {
    private final SellerRepository repository;

    public SellerSQLGateway(final SellerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Seller> findBy(final SellerID id) {
        return this.repository.findById(id.getValue()).map(SellerJpaEntity::toAggregate);
    }
}
