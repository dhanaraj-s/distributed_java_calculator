/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 19, 2021
 *
 */
package com.calculator.user;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.calculator.user.services.AmazonSQSService;

/**
 * The Class CalculatorUserApplicationTests.
 */
@RunWith(SpringRunner.class)	
@SpringBootTest
class CalculatorUserApplicationTests {
	
	/** The amazon SQS service. */
	@Mock
	private AmazonSQSService amazonSQSService; 

	/**
	 * Context loads.
	 */
	@Test
	void contextLoads() {
	}

}
