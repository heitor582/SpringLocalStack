package com.study.verifyPayment.domain;

import com.study.verifyPayment.domain.charges.Charge;
import com.study.verifyPayment.domain.charges.ChargeID;
import com.study.verifyPayment.domain.sellers.Seller;
import com.study.verifyPayment.domain.sellers.SellerID;
import com.study.verifyPayment.domain.utils.InstantUtils;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

public final class Fixture {

    private static final Faker FAKER = new Faker();
    public static String name() {
        return FAKER.name().fullName();
    }
    public static String email() {
        return FAKER.internet().emailAddress();
    }

    public static BigDecimal amount() {
        final BigDecimal MAX_AMOUNT = new BigDecimal("100000.00");
        BigDecimal randomAmount = BigDecimal.valueOf(FAKER.random().nextDouble() * MAX_AMOUNT.doubleValue());

        return randomAmount.setScale(2, RoundingMode.HALF_UP);
    }

    public static Seller seller() {
        return Seller.with(
                SellerID.unique(),
                name(),
                email(),
                InstantUtils.now()
        );
    }

    public static Charge charge(SellerID sellerID) {
        return Charge.with(
                ChargeID.unique(),
                sellerID,
                amount(),
                "",
                Instant.now()
        );
    }
}