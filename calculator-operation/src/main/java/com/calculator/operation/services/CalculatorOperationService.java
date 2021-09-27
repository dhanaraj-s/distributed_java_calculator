/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 26, 2021
 *
 */
package com.calculator.operation.services;

import java.math.BigDecimal;

import com.calculator.operation.model.CalculatorRequest;

/**
 * The Interface CalculatorOperationService.
 */
public interface CalculatorOperationService {
	
	/**
	 * Checks if is operation exists.
	 *
	 * @param request the request
	 * @return true, if is operation exists
	 */
	BigDecimal isOperationExists(CalculatorRequest request);
	
	/**
	 * Perform calculation.
	 *
	 * @param request the request
	 * @return the big decimal
	 * @throws ArithmeticException the arithmetic exception
	 */
	BigDecimal performCalculation(CalculatorRequest request) throws ArithmeticException;

}
