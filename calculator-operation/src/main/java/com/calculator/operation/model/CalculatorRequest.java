/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 19, 2021
 *
 */
package com.calculator.operation.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The Class CalculatorRequest.
 */
public class CalculatorRequest implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The first operand. */
	BigDecimal firstOperand;

	/** The second operand. */
	BigDecimal secondOperand;

	/** The operator. */
	String operator;

	/** The result. */
	BigDecimal result;

	/**
	 * Gets the first operand.
	 *
	 * @return the first operand
	 */
	public BigDecimal getFirstOperand() {
		return firstOperand;
	}

	/**
	 * Sets the first operand.
	 *
	 * @param firstOperand the new first operand
	 */
	public void setFirstOperand(BigDecimal firstOperand) {
		this.firstOperand = firstOperand;
	}

	/**
	 * Gets the second operand.
	 *
	 * @return the second operand
	 */
	public BigDecimal getSecondOperand() {
		return secondOperand;
	}

	/**
	 * Sets the second operand.
	 *
	 * @param secondOperand the new second operand
	 */
	public void setSecondOperand(BigDecimal secondOperand) {
		this.secondOperand = secondOperand;
	}

	/**
	 * Gets the operator.
	 *
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * Sets the operator.
	 *
	 * @param operator the new operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * Gets the result.
	 *
	 * @return the result
	 */
	public BigDecimal getResult() {
		return result;
	}

	/**
	 * Sets the result.
	 *
	 * @param result the new result
	 */
	public void setResult(BigDecimal result) {
		this.result = result;
	}

}
