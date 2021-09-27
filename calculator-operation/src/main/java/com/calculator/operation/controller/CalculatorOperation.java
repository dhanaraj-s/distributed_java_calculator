/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 27, 2021
 *
 */
package com.calculator.operation.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.calculator.operation.model.AmazonAWSConstants;
import com.calculator.operation.model.CalculatorRequest;
import com.calculator.operation.services.CalculatorOperationService;

import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;

/**
 * The Class CalculatorOperationController.
 */
@Service
public class CalculatorOperation {

	/** The logger. */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The calculator operation service. */
	@Autowired
	private CalculatorOperationService calculatorOperationService;

	/**
	 * Process message.
	 *
	 * @param request  the request
	 * @param senderId the sender id
	 */
	@SqsListener(value = AmazonAWSConstants.QUEUE_URL, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	public void processMessage(final CalculatorRequest request, @Header("SenderId") String senderId) {
		BigDecimal result = null;
		try {
			logger.info("Received new SQS message: {}", request, senderId);
			result = this.calculatorOperationService.isOperationExists(request);
			if (null == result) {
				result = this.calculatorOperationService.performCalculation(request);
			}
			logger.info("Calculation result is: {}", result);
		} catch (Exception e) {
			throw new RuntimeException("Cannot process message from SQS", e);
		}
	}

}
