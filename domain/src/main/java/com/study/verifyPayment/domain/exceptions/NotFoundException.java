package com.study.verifyPayment.domain.exceptions;

import com.study.verifyPayment.domain.AggregateRoot;
import com.study.verifyPayment.domain.Identifier;
import com.study.verifyPayment.domain.validation.Error;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NotFoundException extends DomainException{
    protected NotFoundException(final String message, final List<Error> errors) {
        super(message, errors);
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> aggregate,
            final Identifier id
    ) {
        final var error = "%s with ID %s was not found".formatted(
                aggregate.getSimpleName(), id.getValue()
        );

        return new NotFoundException(error, Collections.emptyList());
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> aggregate,
            final Set<Identifier> id
    ) {
        final var error = "%s IDs %s was not found".formatted(
                aggregate.getSimpleName(), id.stream().map(Identifier::getValue).collect(Collectors.joining(", "))
        );

        return new NotFoundException(error, Collections.emptyList());
    }
}