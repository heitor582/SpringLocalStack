package com.study.verifyPayment.application.payments.verifyPayment;

import com.study.verifyPayment.IntegrationTest;
import com.study.verifyPayment.domain.Fixture;
import com.study.verifyPayment.domain.charges.Charge;
import com.study.verifyPayment.domain.charges.ChargeGateway;
import com.study.verifyPayment.domain.payments.PaymentGateway;
import com.study.verifyPayment.domain.sellers.Seller;
import com.study.verifyPayment.domain.sellers.SellerGateway;
import com.study.verifyPayment.infrastructure.charges.persistence.ChargeJpaEntity;
import com.study.verifyPayment.infrastructure.charges.persistence.ChargeRepository;
import com.study.verifyPayment.infrastructure.sellers.persistence.SellerJpaEntity;
import com.study.verifyPayment.infrastructure.sellers.persistence.SellerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;

public class VerifyPaymentUseCaseIT extends IntegrationTest {

    @SpyBean
    private ChargeGateway chargeGateway;
    @SpyBean
    private PaymentGateway paymentGateway;
    @SpyBean
    private SellerGateway sellerGateway;

    @Autowired
    private VerifyPaymentUseCase useCase;

    @Autowired
    private ChargeRepository chargeRepository;
    @Autowired
    private SellerRepository sellerRepository;

    @Test
    public void givenAValidCommand_whenCallsVerifyPayment_shouldCallsPublishEvents() {
        // given
        Seller seller = Fixture.seller();
        Charge charge = Fixture.charge(seller.getId());
        final var aCommand =
                new VerifyPaymentCommand(
                        seller.getId().getValue(),
                        List.of(
                                new PaymentItemCommand(charge.getId().getValue(),  charge.getTotal())
                        )
                );

        // when
        sellerRepository.save(SellerJpaEntity.from(seller));
        chargeRepository.save(ChargeJpaEntity.from(charge));

        assertDoesNotThrow(() -> useCase.execute(aCommand));

        // then
        Mockito.verify(chargeGateway, atLeastOnce()).findBy(any());
        Mockito.verify(paymentGateway, atLeastOnce()).publishEvents(any());
        Mockito.verify(sellerGateway, atLeastOnce()).findBy(seller.getId());
    }
}
