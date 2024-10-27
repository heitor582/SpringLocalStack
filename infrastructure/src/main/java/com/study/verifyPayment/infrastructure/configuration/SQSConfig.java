package com.study.verifyPayment.infrastructure.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.core.env.ResourceIdResolver;
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler;
import org.springframework.cloud.aws.messaging.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

@Configuration
@Import(ObjectMapperConfig.class)
@Profile({"production"})
public class SQSConfig {
    @Value("${cloud.aws.endpoint.uri}")
    private String host;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKeyId;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretAccessKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsync() {
        return AmazonSQSAsyncClientBuilder.standard()
                .withEndpointConfiguration(getEndpointConfiguration())
                .withCredentials(getCredentialsProvider())
                .build();
    }

    @Bean
    public MappingJackson2MessageConverter mappingJackson2MessageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(MappingJackson2MessageConverter converter) {
        return new QueueMessagingTemplate(amazonSQSAsync(), (ResourceIdResolver) null, converter);
    }

    @Bean
    public QueueMessageHandler queueMessageHandler() {
        var queueMessageHandlerFactory = new QueueMessageHandlerFactory();
        queueMessageHandlerFactory.setAmazonSqs(amazonSQSAsync());
        return queueMessageHandlerFactory.createQueueMessageHandler();
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        var simpleListenerContainer = new SimpleMessageListenerContainer();
        simpleListenerContainer.setAmazonSqs(amazonSQSAsync());
        simpleListenerContainer.setMessageHandler(queueMessageHandler());
        return simpleListenerContainer;
    }

    private AwsClientBuilder.EndpointConfiguration getEndpointConfiguration() {
        return new AwsClientBuilder.EndpointConfiguration(host, region);
    }

    public AWSStaticCredentialsProvider getCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKeyId, secretAccessKey));
    }
}
