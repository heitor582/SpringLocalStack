package com.study.verifyPayment.infrastructure.configuration;

import com.study.verifyPayment.infrastructure.services.EventService;
import com.study.verifyPayment.infrastructure.services.impl.NotificationService;
import com.study.verifyPayment.infrastructure.services.local.InMemoryEventService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class EventConfig {

    @Bean
    @Profile({"!production"})
    EventService localInvoiceCreatedEventService() {
        return new InMemoryEventService();
    }

    @Bean
    @ConditionalOnMissingBean
    EventService InvoiceCreatedEventService(
            final QueueMessagingTemplate messagingTemplate
    ) {
        return new NotificationService(messagingTemplate);
    }
}