package com.study.verifyPayment.domain.payments;

import com.study.verifyPayment.domain.Identifier;
import com.study.verifyPayment.domain.utils.IdUtils;

import java.util.Objects;

public class PaymentID extends Identifier {
    private final String value;

    private PaymentID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static PaymentID from(final String id) {
        return new PaymentID(id);
    }

    public static PaymentID unique() {
        return PaymentID.from(IdUtils.uuid());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PaymentID that = (PaymentID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}