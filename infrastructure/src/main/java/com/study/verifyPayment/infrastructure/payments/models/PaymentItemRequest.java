package com.study.verifyPayment.infrastructure.payments.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.verifyPayment.application.payments.verifyPayment.PaymentItemCommand;

import java.math.BigDecimal;

public record PaymentItemRequest(
        @JsonProperty("charge_id") String chargeId,
        @JsonProperty("amount") BigDecimal amount
){
    public PaymentItemCommand toCommand(){
        return new PaymentItemCommand(chargeId, amount);
    }
}
