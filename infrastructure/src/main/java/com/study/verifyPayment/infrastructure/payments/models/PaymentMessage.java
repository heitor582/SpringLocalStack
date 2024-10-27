package com.study.verifyPayment.infrastructure.payments.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.study.verifyPayment.domain.charges.ChargeID;
import com.study.verifyPayment.domain.payments.Payment;
import com.study.verifyPayment.domain.payments.PaymentID;
import com.study.verifyPayment.domain.payments.PaymentStatus;
import com.study.verifyPayment.domain.sellers.SellerID;

import java.math.BigDecimal;
import java.time.Instant;

@JsonSerialize
@JsonDeserialize
public record PaymentMessage(
        String id,
        String charge,
        String seller,
        BigDecimal total,
        PaymentStatus status,
        Instant createdAt
) {
    public Payment toAggregate() {
        return Payment.with(
                PaymentID.from(id),
                ChargeID.from(charge),
                SellerID.from(seller),
                total,
                status,
                createdAt
        );
    }
    public static PaymentMessage from(Payment payment){
        return new PaymentMessage(
                payment.getId().getValue(),
                payment.getCharge().getValue(),
                payment.getSeller().getValue(),
                payment.getTotal(),
                payment.getStatus(),
                payment.getCreatedAt()
        );
    }
}