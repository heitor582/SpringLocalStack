package com.study.verifyPayment.domain.sellers;

import java.util.Optional;

public interface SellerGateway {
    Optional<Seller> findBy(final SellerID id);
}
