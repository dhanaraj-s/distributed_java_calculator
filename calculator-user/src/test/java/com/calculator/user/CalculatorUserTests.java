/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 19, 2021
 *
 */
package com.calculator.user;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.calculator.user.controller.CalculatorUserController;
import com.calculator.user.model.CalculatorRequest;
import com.calculator.user.services.AmazonSQSClient;
import com.calculator.user.services.AmazonSQSService;

import cloud.localstack.docker.LocalstackDockerExtension;
import cloud.localstack.docker.annotation.LocalstackDockerProperties;

/**
 * The Class CalculatorUserTests.
 */
@ExtendWith(LocalstackDockerExtension.class)
@LocalstackDockerProperties(services = { "sqs" })
public class CalculatorUserTests extends CalculatorUserApplicationTests {
	
	/** The calculator user controller. */
	@Autowired
	private CalculatorUserController calculatorUserController;

	/** The amazon SQS client. */
	private AmazonSQSClient amazonSQSClient;
	
	/** The amazon SQS service. */
	@Mock
	private AmazonSQSService amazonSQSService; 
	
    /** The queue messaging template. */
    private QueueMessagingTemplate queueMessagingTemplate;
	
	/**
	 * Initialization for testing.
	 */
	@Before
	public void init() {
        MockitoAnnotations.openMocks(this);
        this.queueMessagingTemplate = new QueueMessagingTemplate(AmazonSQSAsyncClientBuilder.defaultClient());
        amazonSQSClient.createQueue(AmazonSQSAsyncClientBuilder.defaultClient(), "calculate-queue");
    }

	/**
	 * Test perform operation.
	 */
	@Test
    public void testPerformOperation() {
        CalculatorRequest request = new CalculatorRequest(new BigDecimal(1), new BigDecimal(2), "+", null);
        Mockito.when(amazonSQSService.isValid(request)).thenReturn(true);
        Mockito.doNothing().when(queueMessagingTemplate).convertAndSend("calculate-queue", request);
		assertEquals(new ResponseEntity<>("Message publishing to Amazon SQS success", HttpStatus.OK),
				calculatorUserController.performOperation(request));
    }

}
