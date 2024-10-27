package com.study.verifyPayment.e2e;

import com.study.verifyPayment.domain.sellers.SellerID;
import com.study.verifyPayment.infrastructure.configuration.json.Json;
import com.study.verifyPayment.infrastructure.payments.models.PaymentItemRequest;
import com.study.verifyPayment.infrastructure.payments.models.VerifyPaymentsRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

public interface MockDSL {
    String PAYMENTS_URL = "/payments";

    MockMvc mvc();

    default ResultActions givenAVerifyPaymentResult(SellerID sellerID, List<PaymentItemRequest> paymentItemRequests) throws Exception {
        final var body = new VerifyPaymentsRequest(
                sellerID.getValue(),
                paymentItemRequests
        );

        return this.givenResult(PAYMENTS_URL, body);
    }

    private ResultActions givenResult(final String url, final Object body) throws Exception {
        final var request = MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(body));

        return this.mvc().perform(request);
    }

}
