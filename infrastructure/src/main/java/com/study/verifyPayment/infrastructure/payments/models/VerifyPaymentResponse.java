package com.study.verifyPayment.infrastructure.payments.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.verifyPayment.application.payments.verifyPayment.VerifyPaymentOutput;

import java.util.List;

public record VerifyPaymentResponse(
        @JsonProperty("ids") List<String> ids
){
    public static VerifyPaymentResponse from(VerifyPaymentOutput output) {
        return new VerifyPaymentResponse(output.ids());
    }
}
