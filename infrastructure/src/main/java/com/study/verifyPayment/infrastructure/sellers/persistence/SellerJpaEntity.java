package com.study.verifyPayment.infrastructure.sellers.persistence;

import com.study.verifyPayment.domain.sellers.Seller;
import com.study.verifyPayment.domain.sellers.SellerID;
import com.study.verifyPayment.infrastructure.configuration.annotations.GeneratedJpaOnly;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "sellers")
public class SellerJpaEntity {
    @Id
    private String id;
    private String name;
    private String email;
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP(6)")
    private Instant createdAt;

    @GeneratedJpaOnly
    public SellerJpaEntity() {
    }

    public SellerJpaEntity(
            final String id,
            final String name,
            final String email,
            final Instant createdAt
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public static SellerJpaEntity from(final Seller seller) {
        return new SellerJpaEntity(
                seller.getId().getValue(),
                seller.getName(),
                seller.getEmail(),
                seller.getCreatedAt()
        );
    }

    public Seller toAggregate(){
        return Seller.with(
                SellerID.from(this.id),
                this.name,
                this.email,
                this.createdAt
        );
    }
}
