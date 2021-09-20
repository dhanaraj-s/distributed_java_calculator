/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 19, 2021
 *
 */
package com.calculator.user.services;

import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import com.calculator.user.model.CalculatorRequest;

/**
 * The Interface AmazonSQSService.
 */
@Service
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
	 * @throws MessagingException the messaging exception
	 */
	void publishRequest(CalculatorRequest calculatorRequest) throws MessagingException;

}
