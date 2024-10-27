package com.study.verifyPayment.e2e.payments;

import com.study.verifyPayment.E2ETest;
import com.study.verifyPayment.application.payments.verifyPayment.VerifyPaymentUseCase;
import com.study.verifyPayment.domain.Fixture;
import com.study.verifyPayment.domain.charges.Charge;
import com.study.verifyPayment.domain.sellers.Seller;
import com.study.verifyPayment.infrastructure.charges.persistence.ChargeJpaEntity;
import com.study.verifyPayment.infrastructure.charges.persistence.ChargeRepository;
import com.study.verifyPayment.infrastructure.payments.models.PaymentItemRequest;
import com.study.verifyPayment.infrastructure.payments.persistence.PaymentRepository;
import com.study.verifyPayment.infrastructure.sellers.persistence.SellerJpaEntity;
import com.study.verifyPayment.infrastructure.sellers.persistence.SellerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
public class VerifyPaymentE2ETest extends E2ETest {

    @Autowired
    private VerifyPaymentUseCase useCase;

    @Autowired
    private ChargeRepository chargeRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void givenAnInvalidSellerId_whenCallsVerifyPayment_shouldCallsPublishEvents() throws Exception {
        Seller seller = Fixture.seller();
        Charge charge = Fixture.charge(seller.getId());

        var paymentRequest = List.of(
                new PaymentItemRequest(
                        charge.getId().getValue(),
                        charge.getTotal()
                )
        );

        givenAVerifyPaymentResult(seller.getId(), paymentRequest)
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenAValidCommand_whenCallsVerifyPayment_shouldCallsPublishEvents() throws Exception {
        Seller seller = Fixture.seller();
        Charge charge = Fixture.charge(seller.getId());
        Charge charge1 = Fixture.charge(seller.getId());
        Charge charge2 = Fixture.charge(seller.getId());

        var paymentRequest = List.of(
                new PaymentItemRequest(
                        charge.getId().getValue(),
                        charge.getTotal()
                ),
                new PaymentItemRequest(
                        charge1.getId().getValue(),
                        BigDecimal.valueOf(10).add(charge1.getTotal())
                ),
                new PaymentItemRequest(
                        charge2.getId().getValue(),
                        charge2.getTotal().subtract(BigDecimal.valueOf(10))
                )
        );
        sellerRepository.saveAndFlush(SellerJpaEntity.from(seller));
        chargeRepository.saveAllAndFlush(List.of(ChargeJpaEntity.from(charge), ChargeJpaEntity.from(charge1), ChargeJpaEntity.from(charge2)));

       givenAVerifyPaymentResult(seller.getId(), paymentRequest)
               .andExpect(status().isOk());
    }
}
