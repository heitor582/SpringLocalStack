package com.study.verifyPayment.infrastructure.payments;

import com.study.verifyPayment.domain.payments.Payment;
import com.study.verifyPayment.domain.payments.PaymentGateway;
import com.study.verifyPayment.domain.payments.PaymentStatus;
import com.study.verifyPayment.infrastructure.payments.models.PaymentMessage;
import com.study.verifyPayment.infrastructure.payments.persistence.PaymentJpaEntity;
import com.study.verifyPayment.infrastructure.payments.persistence.PaymentRepository;
import com.study.verifyPayment.infrastructure.services.EventService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentServiceGateway implements PaymentGateway {
    private final PaymentRepository repository;
    private final EventService eventService;

    public PaymentServiceGateway(final EventService eventService, final PaymentRepository repository) {
        this.eventService = eventService;
        this.repository = repository;
    }

    @Override
    public Payment save(final Payment payment) {
        return repository.save(PaymentJpaEntity.from(payment)).toAggregate();
    }

    @Override
    public void publishEvents(final List<Payment> payments) {
        payments.forEach(it -> this.eventService.send(queue(it.getStatus()), PaymentMessage.from(it)));
    }

    private String queue(final PaymentStatus status) {
        return "payment-%s-queue".formatted(status.name().toLowerCase());
    }
}
