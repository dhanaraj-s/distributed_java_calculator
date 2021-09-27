/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 26, 2021
 *
 */
package com.calculator.user.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.calculator.user.model.AmazonAWSConstants;

import io.awspring.cloud.messaging.core.QueueMessageChannel;

/**
 * The Class MessageSender.
 */
@Service
public class MessageSender {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);

	/** The amazon sqs. */
	@Autowired
	private final AmazonSQSAsync amazonSqs;

	/**
	 * Instantiates a new message sender.
	 *
	 * @param amazonSQSAsync the amazon SQS async
	 */
	@Autowired
	public MessageSender(final AmazonSQSAsync amazonSQSAsync) {
		this.amazonSqs = amazonSQSAsync;
	}

	/**
	 * Send.
	 *
	 * @param messagePayload the message payload
	 * @return true, if successful
	 */
	public boolean send(final String messagePayload) {
		MessageChannel messageChannel = new QueueMessageChannel(amazonSqs, AmazonAWSConstants.QUEUE_URL);

		Message<String> msg = MessageBuilder.withPayload(messagePayload)
				.setHeader("Sender", "Calculator")
				.setHeaderIfAbsent("Country", "Canada").build();

		long waitTimeoutMillis = 5000;
		boolean sentStatus = messageChannel.send(msg, waitTimeoutMillis);
		logger.info("Message Sent Status: {}", sentStatus);
		return sentStatus;
	}

}
