package com.study.verifyPayment.infrastructure.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.study.verifyPayment.domain.payments.PaymentGateway;
import com.study.verifyPayment.infrastructure.configuration.json.Json;
import com.study.verifyPayment.infrastructure.payments.models.PaymentMessage;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
@Component
public class PaymentPartialConsumer {
    private final PaymentGateway gateway;
    private final String queue  = "payment-partial-queue";

    public PaymentPartialConsumer(final PaymentGateway gateway) {
        this.gateway = gateway;
    }

    @SqsListener(value = queue, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void consume(Message<String> message) throws JsonProcessingException {
        var payload = Json.mapper().readValue(message.getPayload(), PaymentMessage.class);
        gateway.save(payload.toAggregate());
    }
}
