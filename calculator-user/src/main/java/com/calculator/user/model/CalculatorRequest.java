/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 19, 2021
 *
 */
package com.calculator.user.model;

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

	public CalculatorRequest() {
		super();
	}

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

	/**
	 * Instantiates a new calculator request.
	 *
	 * @param firstOperand  the first operand
	 * @param secondOperand the second operand
	 * @param operator      the operator
	 * @param result        the result
	 */
	public CalculatorRequest(BigDecimal firstOperand, BigDecimal secondOperand, String operator, BigDecimal result) {
		super();
		this.firstOperand = firstOperand;
		this.secondOperand = secondOperand;
		this.operator = operator;
		this.result = result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CalculatorRequest [firstOperand=" + firstOperand + ", secondOperand=" + secondOperand + ", operator="
				+ operator + ", result=" + result + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstOperand == null) ? 0 : firstOperand.hashCode());
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((secondOperand == null) ? 0 : secondOperand.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CalculatorRequest other = (CalculatorRequest) obj;
		if (firstOperand == null) {
			if (other.firstOperand != null)
				return false;
		} else if (!firstOperand.equals(other.firstOperand))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (secondOperand == null) {
			if (other.secondOperand != null)
				return false;
		} else if (!secondOperand.equals(other.secondOperand))
			return false;
		return true;
	}
	
}
