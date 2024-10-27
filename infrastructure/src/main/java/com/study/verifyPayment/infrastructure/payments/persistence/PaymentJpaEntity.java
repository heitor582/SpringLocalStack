package com.study.verifyPayment.infrastructure.payments.persistence;

import com.study.verifyPayment.domain.charges.ChargeID;
import com.study.verifyPayment.domain.payments.Payment;
import com.study.verifyPayment.domain.payments.PaymentID;
import com.study.verifyPayment.domain.payments.PaymentStatus;
import com.study.verifyPayment.domain.sellers.SellerID;
import com.study.verifyPayment.infrastructure.configuration.annotations.GeneratedJpaOnly;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payments")
public class PaymentJpaEntity {
    @Id
    private String id;
    @Column(name = "charge_id")
    private String chargeId;
    @Column(name = "seller_id")
    private String sellerId;
    private BigDecimal total;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP(6)")
    private Instant createdAt;

    @GeneratedJpaOnly
    public PaymentJpaEntity() {
    }

    public PaymentJpaEntity(
            final String id,
            final String chargeId,
            final String sellerId,
            final BigDecimal total,
            final PaymentStatus status,
            final Instant createdAt
    ) {
        this.id = id;
        this.chargeId = chargeId;
        this.sellerId = sellerId;
        this.total = total;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static PaymentJpaEntity from(final Payment payment) {
        return new PaymentJpaEntity(
                payment.getId().getValue(),
                payment.getCharge().getValue(),
                payment.getSeller().getValue(),
                payment.getTotal(),
                payment.getStatus(),
                payment.getCreatedAt()
        );
    }

    public Payment toAggregate(){
        return Payment.with(
                PaymentID.from(this.id),
                ChargeID.from(this.chargeId),
                SellerID.from(this.sellerId),
                this.total,
                this.status,
                this.createdAt
        );
    }
}
