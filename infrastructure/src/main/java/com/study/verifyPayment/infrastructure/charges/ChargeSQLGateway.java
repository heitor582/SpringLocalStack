package com.study.verifyPayment.infrastructure.charges;

import com.study.verifyPayment.domain.charges.Charge;
import com.study.verifyPayment.domain.charges.ChargeGateway;
import com.study.verifyPayment.domain.charges.ChargeID;
import com.study.verifyPayment.infrastructure.charges.persistence.ChargeJpaEntity;
import com.study.verifyPayment.infrastructure.charges.persistence.ChargeRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChargeSQLGateway implements ChargeGateway {
    private final ChargeRepository repository;

    public ChargeSQLGateway(final ChargeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Charge> findBy(List<ChargeID> chargeIds) {
        return repository.findAllById(chargeIds.stream().map(ChargeID::getValue).toList()).stream().map(ChargeJpaEntity::toAggregate).toList();
    }
}
