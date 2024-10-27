package com.study.verifyPayment.application.payments.verifyPayment;

import java.util.List;

public record VerifyPaymentCommand(
    String sellerId,
    List<PaymentItemCommand> paymentItemList
) {
}
