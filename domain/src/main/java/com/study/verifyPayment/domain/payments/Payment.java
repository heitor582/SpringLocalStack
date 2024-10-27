package com.study.verifyPayment.domain.payments;

import com.study.verifyPayment.domain.AggregateRoot;
import com.study.verifyPayment.domain.charges.ChargeID;
import com.study.verifyPayment.domain.sellers.SellerID;
import com.study.verifyPayment.domain.utils.InstantUtils;

import java.math.BigDecimal;
import java.time.Instant;

public class Payment extends AggregateRoot<PaymentID> {
    private final ChargeID charge;
    private final SellerID seller;
    private final BigDecimal total;
    private final PaymentStatus status;
    private final Instant createdAt;
    private Payment(final PaymentID paymentID, final ChargeID charge, final SellerID seller, final BigDecimal total, final PaymentStatus status, final Instant createdAt) {
        super(paymentID);
        this.charge = charge;
        this.seller = seller;
        this.total = total;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Payment with(final PaymentID id, final ChargeID charge, final SellerID seller, final BigDecimal total, final PaymentStatus status, final Instant createdAt) {
        return new Payment(id, charge, seller, total, status, createdAt);
    }

    public static Payment newPayment(final ChargeID charge, final SellerID seller, final PaymentStatus status, final BigDecimal total) {
        return new Payment(PaymentID.unique(), charge, seller, total, status, InstantUtils.now());
    }

    public ChargeID getCharge() {
        return charge;
    }

    public SellerID getSeller() {
        return seller;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public PaymentStatus getStatus() {
        return status;
    }
}
