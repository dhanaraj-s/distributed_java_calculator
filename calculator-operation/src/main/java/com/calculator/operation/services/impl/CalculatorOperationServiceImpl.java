/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 19, 2021
 *
 */
package com.calculator.operation.services.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.calculator.operation.entity.CalculationEntity;
import com.calculator.operation.model.CalculatorRequest;
import com.calculator.operation.repository.CalculationRepository;
import com.calculator.operation.services.CalculatorOperationService;

/**
 * The Class CalculatorOperationServiceImpl.
 */
public class CalculatorOperationServiceImpl implements CalculatorOperationService {

	/** The calculation repository. */
	@Autowired
	private CalculationRepository calculationRepository;

	/* (non-Javadoc)
	 * @see com.calculator.operation.services.CalculatorOperationService#isOperationExists(com.calculator.operation.model.CalculatorRequest)
	 */
	@Override
	public boolean isOperationExists(CalculatorRequest request) {
		Optional<CalculationEntity> entity = calculationRepository.findByFirstOperandAndSecondOperandAndOperator(
				request.getFirstOperand(), request.getSecondOperand(), request.getOperator());
		request.setResult(entity.isPresent() ? entity.get().getResult() : null);
		return entity.isPresent();
	}

	/* (non-Javadoc)
	 * @see com.calculator.operation.services.CalculatorOperationService#performCalculation(com.calculator.operation.model.CalculatorRequest)
	 */
	@Override
	public BigDecimal performCalculation(CalculatorRequest request) {
		BigDecimal result = null;
		switch (request.getOperator()) {
		case "+":
			result = request.getFirstOperand().add(request.getSecondOperand());
			break;
		case "-":
			result = request.getFirstOperand().subtract(request.getSecondOperand());
			break;
		case "*":
			result = request.getFirstOperand().multiply(request.getSecondOperand());
			break;
		case "/":
			result = request.getFirstOperand().divide(request.getSecondOperand());
			break;
		default:
			break;
		}
		request.setResult(result);
		calculationRepository.save(populateCalculationEntity(request));
		return result;
	}

	/**
	 * Populate calculation entity.
	 *
	 * @param request the request
	 * @return the calculation entity
	 */
	private CalculationEntity populateCalculationEntity(CalculatorRequest request) {
		CalculationEntity entity = new CalculationEntity();
		entity.setFirstOperand(request.getFirstOperand());
		entity.setSecondOperand(request.getSecondOperand());
		entity.setOperator(request.getOperator());
		entity.setResult(request.getResult());
		return entity;
	}

}
