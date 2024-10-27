package com.study.verifyPayment.infrastructure.services.local;

import com.study.verifyPayment.infrastructure.configuration.json.Json;
import com.study.verifyPayment.infrastructure.services.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryEventService implements EventService {

    private static final Logger LOG = LoggerFactory.getLogger(InMemoryEventService.class);

    @Override
    public void send(final String queue, final Object event) {
        LOG.info("Event was sen to {}t: {}", queue, Json.writeValueAsString(event));
    }
}