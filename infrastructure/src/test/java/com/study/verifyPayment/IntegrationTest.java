package com.study.verifyPayment;

import com.study.verifyPayment.infrastructure.configuration.WebServerConfig;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test-integration")
@SpringBootTest(classes = WebServerConfig.class)
@ExtendWith(SQLCleanUpExtension.class)
@Tag("integrationTest")
public abstract class IntegrationTest {
}