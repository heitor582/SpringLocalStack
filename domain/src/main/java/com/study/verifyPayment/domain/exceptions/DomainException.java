package com.study.verifyPayment.domain.exceptions;


import com.study.verifyPayment.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStackTraceException{
    protected final List<Error> errors;

    protected DomainException(final String message, final List<Error> errors){
        super(message);
        this.errors = errors;
    }

    public static DomainException with(final List<Error> errors) {
        return new DomainException("", errors);
    }

    public static DomainException with(final Error error) {
        return new DomainException(error.message(), List.of(error));
    }

    public List<Error> getErrors() {
        return errors;
    }
}