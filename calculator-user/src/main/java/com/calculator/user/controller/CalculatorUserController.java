/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 19, 2021
 *
 */
package com.calculator.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.calculator.user.model.CalculatorRequest;
import com.calculator.user.services.AmazonSQSService;

/**
 * The Class CalculatorUserController.
 */
@RestController
@RequestMapping(value = "/calculator")
public class CalculatorUserController {

	/** The logger. */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The amazon SQS service. */
	@Autowired
	private AmazonSQSService amazonSQSService;

	/**
	 * Perform operation.
	 *
	 * @param calculatorRequest the calculator request
	 * @return the response entity
	 */
	@PostMapping(value = "/operation")
	public ResponseEntity<String> performOperation(@RequestBody CalculatorRequest calculatorRequest) {
		String response = "Operands or Operator cannot be null. "
				+ "Only supported operations are additions(+), subtraction(-), multiplication(*) and division (/).";
		if (amazonSQSService.isValid(calculatorRequest)) {
			logger.info("Calculator operation \"" + calculatorRequest.getOperator() + "\" for operands "
					+ calculatorRequest.getFirstOperand() + " and " + calculatorRequest.getSecondOperand());
			amazonSQSService.publishRequest(calculatorRequest);
			response = "Message publishing to Amazon SQS success";
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Handle messaging exception.
	 *
	 * @param exception the exception
	 * @return the response entity
	 */
	@ExceptionHandler(MessagingException.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	public ResponseEntity<String> handleMessagingException(MessagingException exception) {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(exception.getMessage());
	}

}
