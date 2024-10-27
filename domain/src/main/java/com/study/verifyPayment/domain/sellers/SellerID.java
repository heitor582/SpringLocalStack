package com.study.verifyPayment.domain.sellers;

import com.study.verifyPayment.domain.Identifier;
import com.study.verifyPayment.domain.utils.IdUtils;

import java.util.Objects;

public class SellerID extends Identifier {
    private final String value;

    private SellerID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static SellerID from(final String id) {
        return new SellerID(id);
    }

    public static SellerID unique() {
        return SellerID.from(IdUtils.uuid());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SellerID that = (SellerID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}