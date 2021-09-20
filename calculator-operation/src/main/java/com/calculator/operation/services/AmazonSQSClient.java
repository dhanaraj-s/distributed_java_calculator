/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 19, 2021
 *
 */
package com.calculator.operation.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

/**
 * The Class AmazonSQSClient.
 */
@Configuration
public class AmazonSQSClient {

	/** The region. */
	@Value("$ {cloud.aws.region.static}")
	private String region;

	/** The aws access key. */
	@Value("$ {cloud.aws.credentials.access-key}")
	private String awsAccessKey;

	/** The aws secret key. */
	@Value("$ {cloud.aws.credentials.secret-key}")
	private String awsSecretKey;

	/** The end point. */
	@Value("$ {cloud.aws.endpoint}")
	private String endPoint;

	/**
	 * Queue messaging template.
	 *
	 * @return the queue messaging template
	 */
	@Bean
	public QueueMessagingTemplate queueMessagingTemplate() {
		return new QueueMessagingTemplate(amazonSQSAsync());
	}

	/**
	 * Amazon SQS async.
	 *
	 * @return the amazon SQS async
	 */
	@Primary
	@Bean
	public AmazonSQSAsync amazonSQSAsync() {
		AmazonSQSAsync async = AmazonSQSAsyncClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
				.withEndpointConfiguration(new EndpointConfiguration(endPoint, region)).build();
		return async;
	}

	/**
	 * Simple message listener container factory.
	 *
	 * @param amazonSQSAsync the amazon SQS async
	 * @return the simple message listener container factory
	 */
	@Bean
	public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(AmazonSQSAsync amazonSQSAsync) {
		SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
		factory.setAmazonSqs(amazonSQSAsync);
		factory.setAutoStartup(true);
		factory.setMaxNumberOfMessages(10);
		factory.setTaskExecutor(createDefaultTaskExecutor());
		return factory;
	}

	/**
	 * Creates the default task executor.
	 *
	 * @return the async task executor
	 */
	protected AsyncTaskExecutor createDefaultTaskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setThreadNamePrefix("SQSExecutor - ");
		threadPoolTaskExecutor.setCorePoolSize(100);
		threadPoolTaskExecutor.setMaxPoolSize(100);
		threadPoolTaskExecutor.setQueueCapacity(2);
		threadPoolTaskExecutor.afterPropertiesSet();
		return threadPoolTaskExecutor;
	}
	
}