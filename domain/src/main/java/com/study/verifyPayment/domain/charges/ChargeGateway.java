package com.study.verifyPayment.domain.charges;

import java.util.List;

public interface ChargeGateway {
    List<Charge> findBy(final List<ChargeID> chargeIds);
}
