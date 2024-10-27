package com.study.verifyPayment.domain.sellers;

import com.study.verifyPayment.domain.AggregateRoot;
import com.study.verifyPayment.domain.utils.InstantUtils;

import java.time.Instant;

public class Seller extends AggregateRoot<SellerID> {
    private final String name;
    private final String email;
    private final Instant createdAt;
    private Seller(final SellerID sellerID, final String name, final String email, final Instant createdAt) {
        super(sellerID);
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public static Seller with(final SellerID id, final String name, final String email, final Instant createdAt) {
        return new Seller(id, name, email, createdAt);
    }

    public Seller newSeller(final String name, final String email){
        return new Seller(SellerID.unique(), name, email, InstantUtils.now());
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
