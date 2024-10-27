package com.study.verifyPayment;

import com.study.verifyPayment.infrastructure.charges.persistence.ChargeRepository;
import com.study.verifyPayment.infrastructure.payments.persistence.PaymentRepository;
import com.study.verifyPayment.infrastructure.sellers.persistence.SellerRepository;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.List;

public class SQLCleanUpExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(final ExtensionContext context) {
        final var appContext = SpringExtension.getApplicationContext(context);

        cleanUp(List.of(
                appContext.getBean(PaymentRepository.class),
                appContext.getBean(ChargeRepository.class),
                appContext.getBean(SellerRepository.class)
        ));
    }

    private void cleanUp(final Collection<CrudRepository> repositories) {
        repositories.forEach(CrudRepository::deleteAll);
    }
}