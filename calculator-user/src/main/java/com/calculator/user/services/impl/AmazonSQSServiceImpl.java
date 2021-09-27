/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 26, 2021
 *
 */
package com.calculator.user.services.impl;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.calculator.user.model.CalculatorRequest;
import com.calculator.user.services.AmazonSQSService;
import com.calculator.user.services.MessageSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class AmazonSQSServiceImpl.
 */
@Service("amazonSQSService")
public class AmazonSQSServiceImpl implements AmazonSQSService {
	
	/** The logger. */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MessageSender messageSender;

	/** The queue. */
	@Value("${cloud.aws.sqs.queue.name}")
	private String queue;

	/** The queue. */
	@Value("${cloud.aws.sqs.queue.url}")
	private String queueEndpoint;

	/** The Constant OPERATIONS. */
	private static final List<String> OPERATIONS = Arrays.asList("+", "-", "*", "/");
	
	/** The Constant OBJECT_MAPPER. */
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.calculator.user.services.AmazonSQSService#isValid(com.calculator.user.
	 * model.CalculatorRequest)
	 */
	@Override
	public boolean isValid(CalculatorRequest calculatorRequest) {
		return !ObjectUtils.isEmpty(calculatorRequest) && calculatorRequest.getFirstOperand() != null
				&& calculatorRequest.getSecondOperand() != null && OPERATIONS.contains(calculatorRequest.getOperator());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.calculator.user.services.AmazonSQSService#publishRequest(com.calculator.
	 * user.model.CalculatorRequest)
	 */
	@Override
	public boolean publishRequest(CalculatorRequest calculatorRequest) throws MessagingException, JsonProcessingException {
		String json = OBJECT_MAPPER.writeValueAsString(calculatorRequest);
		logger.info(json);
		return messageSender.send(json);
	}
}
