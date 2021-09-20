/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 19, 2021
 *
 */
package com.calculator.user.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation .Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.model.PurgeQueueRequest;

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
	
	/** The queue. */
	@Value("${sqs.queue}")
    private String queue;

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
		//createQueues(async, queue);
		return async;
	}
	
	/**
	 * Creates the queue.
	 *
	 * @param amazonSQSAsync the amazon SQS async
	 * @param queueName      the queue name
	 */
	// Creating initial queue so our application can talk to it
	public void createQueue(final AmazonSQSAsync amazonSQSAsync, final String queueName) {
		amazonSQSAsync.createQueue(queueName);
		String queueUrl = amazonSQSAsync.getQueueUrl(queueName).getQueueUrl();
		amazonSQSAsync.purgeQueueAsync(new PurgeQueueRequest(queueUrl));
	}

}