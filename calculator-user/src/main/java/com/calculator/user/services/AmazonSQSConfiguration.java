/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 26, 2021
 *
 */
package com.calculator.user.services;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.calculator.user.model.AmazonAWSConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;

/**
 * The Class AmazonSQSClient.
 */
@Configuration
public class AmazonSQSConfiguration {
	
	
	
	/**
	 * Queue messaging template.
	 *
	 * @param amazonSQSAsync the amazon SQS async
	 * @return the queue messaging template
	 */
	@Bean
	public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {
		return new QueueMessagingTemplate(amazonSQSAsync);
	}
	
	@Bean
    public AwsClientBuilder.EndpointConfiguration endpointConfiguration() {
        return new AwsClientBuilder.EndpointConfiguration(AmazonAWSConstants.ENDPOINT, AmazonAWSConstants.REGION);
    }
		
    @Bean
    @Primary
    public AmazonSQSAsync amazonSqs(final AwsClientBuilder.EndpointConfiguration endpointConfiguration) {
        BasicAWSCredentials credentials = new BasicAWSCredentials(AmazonAWSConstants.ACCESS_KEY, AmazonAWSConstants.SECRET_KEY);
        return AmazonSQSAsyncClientBuilder
                .standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

	/**
	 * Queue message handler factory.
	 *
	 * @param mapper         the mapper
	 * @param amazonSQSAsync the amazon SQS async
	 * @return the queue message handler factory
	 */
	@Bean
	public QueueMessageHandlerFactory queueMessageHandlerFactory(final ObjectMapper mapper,
			final AmazonSQSAsync amazonSQSAsync) {
		final QueueMessageHandlerFactory queueHandlerFactory = new QueueMessageHandlerFactory();
		queueHandlerFactory.setAmazonSqs(amazonSQSAsync);
		queueHandlerFactory.setArgumentResolvers(
				Collections.singletonList(new PayloadMethodArgumentResolver(jackson2MessageConverter(mapper))));
		return queueHandlerFactory;
	}

	/**
	 * Jackson 2 message converter.
	 *
	 * @param mapper the mapper
	 * @return the message converter
	 */
	private MessageConverter jackson2MessageConverter(final ObjectMapper mapper) {
		final MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		// set strict content type match to false to enable the listener to handle AWS events
		converter.setStrictContentTypeMatch(false);
		converter.setObjectMapper(mapper);
		return converter;
	}

}