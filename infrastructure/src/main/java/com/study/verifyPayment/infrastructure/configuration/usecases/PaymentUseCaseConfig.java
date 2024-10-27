package com.study.verifyPayment.infrastructure.configuration.usecases;

import com.study.verifyPayment.application.payments.verifyPayment.DefaultVerifyPaymentUseCase;
import com.study.verifyPayment.application.payments.verifyPayment.VerifyPaymentUseCase;
import com.study.verifyPayment.domain.charges.ChargeGateway;
import com.study.verifyPayment.domain.payments.PaymentGateway;
import com.study.verifyPayment.domain.sellers.SellerGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class PaymentUseCaseConfig {
    private final SellerGateway sellerGateway;
    private final ChargeGateway chargeGateway;
    private final PaymentGateway paymentGateway;

    public PaymentUseCaseConfig(final SellerGateway sellerGateway, final ChargeGateway chargeGateway, final PaymentGateway paymentGateway) {
        this.sellerGateway = Objects.requireNonNull(sellerGateway);
        this.chargeGateway = Objects.requireNonNull(chargeGateway);
        this.paymentGateway = Objects.requireNonNull(paymentGateway);
    }

    @Bean
    public VerifyPaymentUseCase validatePaymentUseCase() {
        return new DefaultVerifyPaymentUseCase(paymentGateway, sellerGateway, chargeGateway);
    }
}
