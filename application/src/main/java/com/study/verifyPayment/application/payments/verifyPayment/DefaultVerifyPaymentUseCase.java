package com.study.verifyPayment.application.payments.verifyPayment;

import com.study.verifyPayment.domain.charges.Charge;
import com.study.verifyPayment.domain.charges.ChargeGateway;
import com.study.verifyPayment.domain.charges.ChargeID;
import com.study.verifyPayment.domain.exceptions.NotFoundException;
import com.study.verifyPayment.domain.payments.Payment;
import com.study.verifyPayment.domain.payments.PaymentGateway;
import com.study.verifyPayment.domain.payments.PaymentStatus;
import com.study.verifyPayment.domain.sellers.Seller;
import com.study.verifyPayment.domain.sellers.SellerGateway;
import com.study.verifyPayment.domain.sellers.SellerID;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultVerifyPaymentUseCase extends VerifyPaymentUseCase {
    private final PaymentGateway paymentGateway;
    private final SellerGateway sellerGateway;
    private final ChargeGateway chargeGateway;

    public DefaultVerifyPaymentUseCase(final PaymentGateway paymentGateway, final SellerGateway sellerGateway, final ChargeGateway chargeGateway) {
        this.paymentGateway = Objects.requireNonNull(paymentGateway);
        this.sellerGateway = Objects.requireNonNull(sellerGateway);
        this.chargeGateway = Objects.requireNonNull(chargeGateway);
    }

    @Override
    public VerifyPaymentOutput execute(final VerifyPaymentCommand cmd) {
        SellerID sellerID = SellerID.from(cmd.sellerId());
        Set<String> listChargeIds = cmd.paymentItemList().stream().map(PaymentItemCommand::chargeId).collect(Collectors.toSet());

        this.sellerGateway.findBy(sellerID)
                .orElseThrow(() -> NotFoundException.with(Seller.class, sellerID));

        List<Charge> charges = this.chargeGateway.findBy(listChargeIds.stream().map(ChargeID::from).toList());

        if(listChargeIds.size() != charges.size()) {
            List<String> foundChargeIds = charges.stream().map(it -> it.getId().getValue()).toList();
            foundChargeIds.forEach(listChargeIds::remove);
            throw NotFoundException.with(Charge.class, listChargeIds.stream().map(ChargeID::from).collect(Collectors.toSet()));
        }

        Map<String, BigDecimal> chargeIdToAmount = charges.stream()
                .collect(Collectors.toMap(item -> item.getId().getValue(), Charge::getTotal));

        List<Payment> payments = cmd.paymentItemList().stream().map(it -> Payment.newPayment(
                    ChargeID.from(it.chargeId()),
                        sellerID,
                        PaymentStatus.from(it.amount(), chargeIdToAmount.get(it.chargeId())),
                        it.amount()

                )).toList();

        paymentGateway.publishEvents(payments);
        return VerifyPaymentOutput.from(payments);
    }
}
