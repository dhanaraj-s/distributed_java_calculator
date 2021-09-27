/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 19, 2021
 *
 */
package com.calculator.user.services;

import org.springframework.messaging.MessagingException;

import com.calculator.user.model.CalculatorRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * The Interface AmazonSQSService.
 */
public interface AmazonSQSService {

	/**
	 * Checks if is valid.
	 *
	 * @param calculatorRequest the calculator request
	 * @return true, if is valid
	 */
	boolean isValid(CalculatorRequest calculatorRequest);
	
	/**
	 * Publish request.
	 *
	 * @param calculatorRequest the calculator request
	 * @return 
	 * @throws MessagingException the messaging exception
	 * @throws JsonProcessingException 
	 */
	boolean publishRequest(CalculatorRequest calculatorRequest) throws MessagingException, JsonProcessingException;

}
