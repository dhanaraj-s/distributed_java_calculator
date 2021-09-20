/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 19, 2021
 *
 */
package com.calculator.operation.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class CalculationEntity.
 */
@Entity
@Table(name = "calculation")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, 
        allowGetters = true)
public class CalculationEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	/** The first operand. */
	@Column(nullable = false)
    private BigDecimal firstOperand;

	/** The second operand. */
	@Column(nullable = false)
    private BigDecimal secondOperand;
    
	/** The operator. */
	@Column(nullable = false)
    private String operator;
    
	/** The result. */
	@Column(nullable = false)
    private BigDecimal result;

    /** The created at. */
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    /** The updated at. */
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * Gets the created at.
	 *
	 * @return the created at
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the created at.
	 *
	 * @param createdAt the new created at
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the updated at.
	 *
	 * @return the updated at
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * Sets the updated at.
	 *
	 * @param updatedAt the new updated at
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
