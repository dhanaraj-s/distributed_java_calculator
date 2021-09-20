/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 19, 2021
 *
 */
package com.calculator.operation.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Controller;

import com.calculator.operation.model.CalculatorRequest;
import com.calculator.operation.services.CalculatorOperationService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class CalculatorOperationController.
 */
@Controller
public class CalculatorOperationController {

	/** The logger. */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The Constant OBJECT_MAPPER. */
	private static final ObjectMapper OBJECT_MAPPER = Jackson2ObjectMapperBuilder.json().build();

	/** The calculator operation service. */
	private CalculatorOperationService calculatorOperationService;

	/**
	 * Process message.
	 *
	 * @param message the message
	 * @return the response entity
	 */
	@SqsListener(value = "${sqs.queue}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	public ResponseEntity<BigDecimal> processMessage(String message) {
		BigDecimal result = null;
		try {
			logger.debug("Received new SQS message: {}", message);
			CalculatorRequest request = OBJECT_MAPPER.readValue(message, CalculatorRequest.class);
			if (!this.calculatorOperationService.isOperationExists(request)) {
				result = request.getResult();
			} else {
				result = this.calculatorOperationService.performCalculation(request);
			}
		} catch (Exception e) {
			throw new RuntimeException("Cannot process message from SQS", e);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
