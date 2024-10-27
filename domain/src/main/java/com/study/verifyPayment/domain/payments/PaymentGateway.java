package com.study.verifyPayment.domain.payments;

import java.util.List;

public interface PaymentGateway {
    Payment save(final Payment payment);
    void publishEvents(final List<Payment> payments);
}
