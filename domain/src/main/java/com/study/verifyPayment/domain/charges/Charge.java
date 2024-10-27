package com.study.verifyPayment.domain.charges;

import com.study.verifyPayment.domain.AggregateRoot;
import com.study.verifyPayment.domain.sellers.SellerID;
import com.study.verifyPayment.domain.utils.InstantUtils;

import java.math.BigDecimal;
import java.time.Instant;

public class Charge extends AggregateRoot<ChargeID> {
    private final SellerID seller;
    private final BigDecimal total;
    private final String description;
    private final Instant createdAt;
    private Charge(final ChargeID chargeID, final SellerID seller, final BigDecimal total, final String description, final Instant createdAt) {
        super(chargeID);
        this.seller = seller;
        this.total = total;
        this.description = description;
        this.createdAt = createdAt;
    }

    public static Charge newCharge(final SellerID seller, final BigDecimal total, final String description){
        return new Charge(ChargeID.unique(), seller, total, description, InstantUtils.now());
    }

    public static Charge with(final ChargeID chargeID, final SellerID seller, final BigDecimal total, final String description, final Instant createdAt) {
        return new Charge(chargeID, seller, total, description, createdAt);
    }

    public SellerID getSeller() {
        return seller;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
