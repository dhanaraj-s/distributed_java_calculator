/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 19, 2021
 *
 */
package com.calculator.user.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.MessagingException;
import org.springframework.util.ObjectUtils;

import com.calculator.user.model.CalculatorRequest;
import com.calculator.user.services.AmazonSQSService;

/**
 * The Class AmazonSQSServiceImpl.
 */
public class AmazonSQSServiceImpl implements AmazonSQSService {
	
	/** The queue messaging template. */
	@Autowired
    private QueueMessagingTemplate queueMessagingTemplate;
	
	/** The queue. */
	@Value("${sqs.queue}")
    private String queue;
	
	/** The Constant OPERATIONS. */
	private static final List<String> OPERATIONS = Arrays.asList("+", "-", "*", "/");

	/* (non-Javadoc)
	 * @see com.calculator.user.services.AmazonSQSService#isValid(com.calculator.user.model.CalculatorRequest)
	 */
	@Override
	public boolean isValid(CalculatorRequest calculatorRequest) {
		return !ObjectUtils.isEmpty(calculatorRequest) && calculatorRequest.getFirstOperand() != null
				&& calculatorRequest.getSecondOperand() != null && OPERATIONS.contains(calculatorRequest.getOperator());
	}

	/* (non-Javadoc)
	 * @see com.calculator.user.services.AmazonSQSService#publishRequest(com.calculator.user.model.CalculatorRequest)
	 */
	@Override
	public void publishRequest(CalculatorRequest calculatorRequest) throws MessagingException {
		queueMessagingTemplate.convertAndSend(queue, calculatorRequest);
	}
}
