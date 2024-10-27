package com.study.verifyPayment.infrastructure.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.study.verifyPayment.infrastructure.configuration.json.Json;
import com.study.verifyPayment.infrastructure.services.EventService;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;

public class NotificationService implements EventService {
    private final QueueMessagingTemplate messagingTemplate;

    public NotificationService(final QueueMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void send(final String queue, final Object event) {
        try {
            String jsonMessage = Json.mapper().writeValueAsString(event);
            messagingTemplate.convertAndSend(queue, jsonMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize event", e);
        }
    }
}
