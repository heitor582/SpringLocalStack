package com.study.verifyPayment.infrastructure.payments.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.verifyPayment.application.payments.verifyPayment.VerifyPaymentCommand;

import java.util.List;

public record VerifyPaymentsRequest(
        @JsonProperty("seller_id") String sellerId,
        @JsonProperty("payment_item") List<PaymentItemRequest> paymentItem
) {
    public VerifyPaymentCommand toCommand(){
        return new VerifyPaymentCommand(
                sellerId,
                paymentItem.stream().map(PaymentItemRequest::toCommand).toList()
        );
    }
}
