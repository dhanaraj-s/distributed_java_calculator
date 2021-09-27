/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 26, 2021
 *
 */
package com.calculator.operation.services.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calculator.operation.entity.CalculationEntity;
import com.calculator.operation.model.CalculatorRequest;
import com.calculator.operation.repository.CalculationRepository;
import com.calculator.operation.services.CalculatorOperationService;

/**
 * The Class CalculatorOperationServiceImpl.
 */
@Service("calculatorOperationService")
public class CalculatorOperationServiceImpl implements CalculatorOperationService {

	/** The calculation repository. */
	@Autowired
	private CalculationRepository calculationRepository;
	
	/** The Constant mathContext. */
	private static final MathContext mathContext = new MathContext(20, RoundingMode.HALF_UP);

	/* (non-Javadoc)
	 * @see com.calculator.operation.services.CalculatorOperationService#isOperationExists(com.calculator.operation.model.CalculatorRequest)
	 */
	@Override
	public BigDecimal isOperationExists(CalculatorRequest request) {
		CalculationEntity entity = calculationRepository.findByFirstOperandAndSecondOperandAndOperator(
				request.getFirstOperand(), request.getSecondOperand(), request.getOperator());
		return entity == null ? null : entity.getResult();
	}

	/* (non-Javadoc)
	 * @see com.calculator.operation.services.CalculatorOperationService#performCalculation(com.calculator.operation.model.CalculatorRequest)
	 */
	@Override
	public BigDecimal performCalculation(CalculatorRequest request) throws ArithmeticException {
		BigDecimal result = null;
		switch (request.getOperator()) {
		case "+":
			result = request.getFirstOperand().add(request.getSecondOperand(), mathContext).setScale(10,
					RoundingMode.HALF_UP);
			break;
		case "-":
			result = request.getFirstOperand().subtract(request.getSecondOperand(), mathContext).setScale(10,
					RoundingMode.HALF_UP);
			break;
		case "*":
			result = request.getFirstOperand().multiply(request.getSecondOperand(), mathContext).setScale(10,
					RoundingMode.HALF_UP);
			break;
		case "/":
			result = request.getFirstOperand().divide(request.getSecondOperand(), mathContext).setScale(10,
					RoundingMode.HALF_UP);
			break;
		default:
			break;
		}
		if (null != result)
			calculationRepository.save(populateCalculationEntity(request, result));
		
		return result;
	}

	/**
	 * Populate calculation entity.
	 *
	 * @param request the request
	 * @param result  the result
	 * @return the calculation entity
	 */
	private CalculationEntity populateCalculationEntity(CalculatorRequest request, BigDecimal result) {
		CalculationEntity entity = new CalculationEntity();
		entity.setFirstOperand(request.getFirstOperand());
		entity.setSecondOperand(request.getSecondOperand());
		entity.setOperator(request.getOperator());
		entity.setResult(result);
		entity.setCreatedAt(new Date());
		entity.setUpdatedAt(new Date());
		return entity;
	}

}
