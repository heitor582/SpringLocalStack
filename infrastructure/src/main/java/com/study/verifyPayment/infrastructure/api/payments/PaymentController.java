package com.study.verifyPayment.infrastructure.api.payments;

import com.study.verifyPayment.application.payments.verifyPayment.VerifyPaymentUseCase;
import com.study.verifyPayment.infrastructure.api.PaymentsAPI;
import com.study.verifyPayment.infrastructure.payments.models.VerifyPaymentResponse;
import com.study.verifyPayment.infrastructure.payments.models.VerifyPaymentsRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class PaymentController implements PaymentsAPI {
    private final VerifyPaymentUseCase verifyPaymentUseCase;

    public PaymentController(final VerifyPaymentUseCase verifyPaymentUseCase) {
        this.verifyPaymentUseCase = Objects.requireNonNull(verifyPaymentUseCase);
    }

    @Override
    public ResponseEntity<VerifyPaymentResponse> verify(VerifyPaymentsRequest request) {
        var output = VerifyPaymentResponse.from(this.verifyPaymentUseCase.execute(request.toCommand()));
        return ResponseEntity.ok(output);
    }
}
