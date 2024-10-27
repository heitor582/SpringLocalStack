package com.study.verifyPayment.infrastructure.charges.persistence;

import com.study.verifyPayment.domain.charges.Charge;
import com.study.verifyPayment.domain.charges.ChargeID;
import com.study.verifyPayment.domain.sellers.SellerID;
import com.study.verifyPayment.infrastructure.configuration.annotations.GeneratedJpaOnly;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "charges")
public class ChargeJpaEntity {
    @Id
    private String id;
    @Column(name = "seller_id")
    private String sellerId;
    private BigDecimal total;
    private String description;
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP(6)")
    private Instant createdAt;

    @GeneratedJpaOnly
    public ChargeJpaEntity() {
    }
    public ChargeJpaEntity(
            final String id,
            final String sellerId,
            final BigDecimal total,
            final String description,
            final Instant createdAt
    ) {
        this.id = id;
        this.sellerId = sellerId;
        this.total = total;
        this.description = description;
        this.createdAt = createdAt;
    }

    public static ChargeJpaEntity from(final Charge charge) {
        return new ChargeJpaEntity(
                charge.getId().getValue(),
                charge.getSeller().getValue(),
                charge.getTotal(),
                charge.getDescription(),
                charge.getCreatedAt()
        );
    }

    public Charge toAggregate() {
        return Charge.with(ChargeID.from(this.id), SellerID.from(this.sellerId), total, description, createdAt);
    }
}
