package com.study.VerifyPayment.application.payments.verifyPayment;

import com.study.VerifyPayment.application.UseCaseTest;
import com.study.verifyPayment.application.payments.verifyPayment.DefaultVerifyPaymentUseCase;
import com.study.verifyPayment.application.payments.verifyPayment.PaymentItemCommand;
import com.study.verifyPayment.application.payments.verifyPayment.VerifyPaymentCommand;
import com.study.verifyPayment.domain.Fixture;
import com.study.verifyPayment.domain.charges.Charge;
import com.study.verifyPayment.domain.charges.ChargeGateway;
import com.study.verifyPayment.domain.exceptions.NotFoundException;
import com.study.verifyPayment.domain.payments.PaymentGateway;
import com.study.verifyPayment.domain.sellers.Seller;
import com.study.verifyPayment.domain.sellers.SellerGateway;
import com.study.verifyPayment.domain.sellers.SellerID;
import com.study.verifyPayment.domain.utils.IdUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class VerifyPaymentUseCaseTest extends UseCaseTest {
    @Mock
    private ChargeGateway chargeGateway;
    @Mock
    private PaymentGateway paymentGateway;
    @Mock
    private SellerGateway sellerGateway;
    @InjectMocks
    private DefaultVerifyPaymentUseCase useCase;

    @Override
    protected List<Object> getMocks() {
        return List.of(chargeGateway, paymentGateway, sellerGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsVerifyPayment_shouldCallsPublishEvents() {
        // given
        Seller seller = Fixture.seller();
        Charge charge = Fixture.charge(seller.getId());
        final var aCommand =
                new VerifyPaymentCommand(
                        seller.getId().getValue(),
                        List.of(
                                new PaymentItemCommand(charge.getId().getValue(),  Fixture.amount())
                        )
                );

        // when
        when(sellerGateway.findBy(seller.getId()))
                .thenReturn(Optional.of(seller));

        when(chargeGateway.findBy(any()))
                .thenReturn(List.of(charge));

        assertDoesNotThrow(() -> useCase.execute(aCommand));

        // then

        Mockito.verify(chargeGateway, atLeastOnce()).findBy(any());
        Mockito.verify(paymentGateway, atLeastOnce()).publishEvents(any());
        Mockito.verify(sellerGateway, atLeastOnce()).findBy(seller.getId());
    }

    @Test
    public void givenAnInvalidSellerId_whenCallsVerifyPayment_shouldReturnNotFoundException() {
        // given
        String sellerID = IdUtils.uuid();
        final var aCommand =
                new VerifyPaymentCommand(
                        sellerID,
                        List.of(new PaymentItemCommand(IdUtils.uuid(),  BigDecimal.valueOf(100)))
                );

        String message = "%s with ID %s was not found".formatted(
                Seller.class.getSimpleName(), sellerID
        );

        // when

        when(sellerGateway.findBy(SellerID.from(sellerID)))
                .thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(NotFoundException.class, () -> useCase.execute(aCommand));

        // then
        Assertions.assertNotNull(actualException);
        assertEquals(message, actualException.getMessage());

        Mockito.verify(chargeGateway, times(0)).findBy(any());
        Mockito.verify(paymentGateway, times(0)).publishEvents(any());
        Mockito.verify(sellerGateway, atLeastOnce()).findBy(SellerID.from(sellerID));
    }

    @Test
    public void givenAnInvalidChargeId_whenCallsVerifyPayment_shouldReturnNotFoundException() {
        // given
        Seller seller = Fixture.seller();
        Charge charge = Fixture.charge(seller.getId());
        String unknownChargeId = IdUtils.uuid();
        final var aCommand =
                new VerifyPaymentCommand(
                        seller.getId().getValue(),
                        List.of(
                                new PaymentItemCommand(unknownChargeId,  Fixture.amount()),
                                new PaymentItemCommand(charge.getId().getValue(),  Fixture.amount())
                        )
                );

        String message = "%s IDs %s was not found".formatted(
                Charge.class.getSimpleName(), unknownChargeId);

        // when

        when(sellerGateway.findBy(seller.getId()))
                .thenReturn(Optional.of(seller));

        when(chargeGateway.findBy(any()))
                .thenReturn(List.of(charge));

        final var actualException = Assertions.assertThrows(NotFoundException.class, () -> useCase.execute(aCommand));

        // then
        Assertions.assertNotNull(actualException);
        assertEquals(message, actualException.getMessage());

        Mockito.verify(chargeGateway, atLeastOnce()).findBy(any());
        Mockito.verify(paymentGateway, times(0)).publishEvents(any());
        Mockito.verify(sellerGateway, atLeastOnce()).findBy(seller.getId());
    }
}
