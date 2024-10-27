package com.study.verifyPayment.application.payments.verifyPayment;

import java.math.BigDecimal;

public record PaymentItemCommand(
        String chargeId,
        BigDecimal amount
) {
}
