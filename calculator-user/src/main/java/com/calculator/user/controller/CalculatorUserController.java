/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 27, 2021
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.calculator.user.model.CalculatorRequest;
import com.calculator.user.services.AmazonSQSService;
import com.fasterxml.jackson.core.JsonProcessingException;

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
	 * @throws MessagingException      the messaging exception
	 * @throws JsonProcessingException the json processing exception
	 */
	@PostMapping(value = "/operation")
	public ResponseEntity<String> performOperation(@RequestBody CalculatorRequest calculatorRequest)
			throws MessagingException, JsonProcessingException {
		String response = "Operands or Operator cannot be null. "
				+ "Only supported operations are additions(+), subtraction(-), multiplication(*) and division (/).";
		if (amazonSQSService.isValid(calculatorRequest)) {
			logger.info("Calculator operation \"" + calculatorRequest.getOperator() + "\" for operands "
					+ calculatorRequest.getFirstOperand() + " and " + calculatorRequest.getSecondOperand());
			boolean status = amazonSQSService.publishRequest(calculatorRequest);
			response = status ? "Message publishing to Amazon SQS success" : "Message publishing to Amazon SQS Failed";
			logger.info("Status : {}", response);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Check app health.
	 *
	 * @return the string
	 */
	@GetMapping(value = "/status")
	public String checkAppHealth() {
		return "Calculator Application is Up And Running";
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
		logger.error("Error while publishing the message: {}", exception.getMessage());
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(exception.getMessage());
	}

}
