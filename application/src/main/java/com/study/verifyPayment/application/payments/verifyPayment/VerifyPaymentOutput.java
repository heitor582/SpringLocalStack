package com.study.verifyPayment.application.payments.verifyPayment;

import com.study.verifyPayment.domain.payments.Payment;

import java.util.List;

public record VerifyPaymentOutput(
        List<String> ids
) {
    public static VerifyPaymentOutput from(final List<Payment> payments){
        return new VerifyPaymentOutput(payments.stream().map(it -> it.getId().getValue()).toList());
    }
}
