/*
 * Copyright (c) 2021 by Dhanaraj S. All rights reserved.
 *
 * Sep 27, 2021
 *
 */
package com.calculator.operation.model;

import org.springframework.beans.factory.annotation.Value;

/**
 * The Class AmazonAWSConstants.
 */
public class AmazonAWSConstants {

	/** The Constant QUEUE_NAME. */
	@Value("${cloud.aws.sqs.queue.name}")
	public static final String QUEUE_NAME = "calculation-queue";

	/** The Constant QUEUE_URL. */
	@Value("${cloud.aws.sqs.queue.url}")
	public static final String QUEUE_URL = "http://localstack:4566/000000000000/calculation-queue";

	/** The Constant ACCESS_KEY. */
	@Value("${cloud.aws.credentials.access-key}")
	public static final String ACCESS_KEY = "foo";

	/** The Constant SECRET_KEY. */
	@Value("${cloud.aws.credentials.secret-key}")
	public static final String SECRET_KEY = "bar";

	/** The Constant ENDPOINT. */
	public static final String ENDPOINT = "http://localstack:4566";

	/** The Constant REGION. */
	public static final String REGION = "us-east-1";

}
