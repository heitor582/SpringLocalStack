package com.study.verifyPayment.domain.charges;

import com.study.verifyPayment.domain.Identifier;
import com.study.verifyPayment.domain.utils.IdUtils;

import java.util.Objects;

public class ChargeID extends Identifier {
    private final String value;

    private ChargeID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static ChargeID from(final String id) {
        return new ChargeID(id);
    }

    public static ChargeID unique() {
        return ChargeID.from(IdUtils.uuid());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ChargeID that = (ChargeID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
