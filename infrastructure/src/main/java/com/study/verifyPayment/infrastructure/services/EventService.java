package com.study.verifyPayment.infrastructure.services;

public interface EventService {
    void send(final String queue, final Object event);
}
