/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 26, 2021
 *
 */
package com.calculator.operation.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calculator.operation.entity.CalculationEntity;

/**
 * The Interface CalculationRepository.
 */
@Repository
public interface CalculationRepository extends JpaRepository<CalculationEntity, Long> {

	/**
	 * Find by first operand and second operand and operator.
	 *
	 * @param firstOperand  the first operand
	 * @param secondOperand the second operand
	 * @param operator      the operator
	 */
	CalculationEntity findByFirstOperandAndSecondOperandAndOperator(BigDecimal firstOperand, BigDecimal secondOperand,
			String operator);

}
