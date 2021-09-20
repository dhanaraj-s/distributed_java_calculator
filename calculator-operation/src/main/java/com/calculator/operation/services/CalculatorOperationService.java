/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 19, 2021
 *
 */
package com.calculator.operation.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.calculator.operation.model.CalculatorRequest;

/**
 * The Interface CalculatorOperationService.
 */
@Service
public interface CalculatorOperationService {
	
	/**
	 * Checks if is operation exists.
	 *
	 * @param request the request
	 * @return true, if is operation exists
	 */
	boolean isOperationExists(CalculatorRequest request);
	
	/**
	 * Perform calculation.
	 *
	 * @param request the request
	 * @return the big decimal
	 */
	BigDecimal performCalculation(CalculatorRequest request);

}
