package com.study.verifyPayment.domain.payments;

import java.math.BigDecimal;

public enum PaymentStatus {
    TOTAL,
    PARTIAL,
    SURPLUS;

    public static PaymentStatus from(final BigDecimal paymentValue, final BigDecimal chargeValue) {
        int compare = paymentValue.compareTo(chargeValue);
        if(compare < 0) {
            return PARTIAL;
        } else if (compare > 0) {
            return SURPLUS;
        } else {
            return TOTAL;
        }
    }
}
