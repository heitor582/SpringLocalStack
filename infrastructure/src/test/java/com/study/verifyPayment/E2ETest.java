package com.study.verifyPayment;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.verifyPayment.e2e.MockDSL;
import com.study.verifyPayment.infrastructure.configuration.ObjectMapperConfig;
import com.study.verifyPayment.infrastructure.configuration.WebServerConfig;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

@SpringBootTest(classes = WebServerConfig.class)
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("e2e")
@ExtendWith(SQLCleanUpExtension.class)
@Import(ObjectMapperConfig.class)
@Tag("e2eTest")
public abstract class E2ETest implements MockDSL {
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper mapper;

    private static final PostgreSQLContainer<?> postgreSQLContainer;
    private static final LocalStackContainer sqsContainer;

    static {
        postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres")
                .withDatabaseName("verify_payment")
                .withUsername("verify_payment")
                .withPassword("verify_payment")
                .withReuse(true);
        postgreSQLContainer.start();
        sqsContainer = new LocalStackContainer(DockerImageName.parse("localstack/localstack"))
                .withClasspathResourceMapping("scripts/init-aws.sh",
                        "/etc/localstack/init/ready.d/init-aws.sh", BindMode.READ_WRITE)
                .withServices(LocalStackContainer.Service.SQS)
                .waitingFor(Wait.forLogMessage(".*Ready.*\\n", 1).withStartupTimeout(Duration.ofMinutes(1)));

        sqsContainer.start();
    }

    @DynamicPropertySource
    private static void setProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("cloud.aws.endpoint.uri", sqsContainer::getEndpoint);
        registry.add("cloud.aws.endpoint.access-key", sqsContainer::getAccessKey);
        registry.add("cloud.aws.endpoint.secret-key", sqsContainer::getSecretKey);
        registry.add("cloud.aws.region.static", () -> "us-east-1");
        registry.add("spring.flyway.enabled", () -> false);
    }

    @Override
    public MockMvc mvc() {
        return this.mvc;
    }
}