# Distributed calculator.

![](./diagram.png)

## Services description.

This task aims to create two services that will perform math operations in a distributed way. Services should support such binary operations:
- addition
- subtraction
- multiplication
- division

## User Facing REST API. 
Service handles user requests. Endpoint takes operation
Parameters. When the service receives a request, it performs validation and checking of user input parameters and, if they are valid, sends an event to the `calculation-queue` queue.

## Calculation Service. 
Consuming messages from the `calculation-queue`, performs calculation and stores input/output in DB. If the operation was calculated previously, skip the calculation step and use the previously computed result.

## Requirements
- Use provided docker-compose as a starting point.
- Amazon SQS should be used as a message bus.
- MySQL should be used as DB. 
- Each service must be a separate unit with the possibility of running in its own Docker container, as all of them should start through docker-compose.


Please choose frameworks, databases, and ORM at its discretion. 

## Useful links:

- https://github.com/localstack/localstack
- https://aws.amazon.com/sqs/faqs/

# Implementation of above Requirements:
# Few things to note while bringing this event driven architecture up using docker-compose

Create a network with overlay driver mode to share across the Springboot Applications, MySQL and LocalStack

- networks:
	dev-network:
	  driver: overlay

- Specify the networks for all services so that all the containers share the same network. 

- I used Docker for windows to run the application, with Linux containers (If the Linux containers are facing OutOfMemory issues, Docker > Settings > Advanced > Memory, reduce the allocated memory to around 1280 MB to run the Linux container)
  If you are still facing issues, After setting the memory, switch to windows container and switch back to Linux container.

Run the Docker Engine in swarm mode. => Docker swarm init

Keep separate Docker file in both the Spring boot applications to run them as separate containers. Keep the docker-compose.yml outside of both the services to run all the containers with single execution.

- If you receive the below error while running the docker-compose up command,

ERROR: for localstack  Cannot create container for service localstack: b'Mount denied:\nThe source path "\\\\tmp\\\\localstack:/tmp/localstack"\nis not a valid Windows path'
ERROR: Encountered errors while bringing up the project.

- set the environment variable using => set COMPOSE_CONVERT_WINDOWS_PATHS=1

Update the Windows Host file (C:\Windows\System32\drivers\etc\hosts) to bring up the Localstack with the below entry

- 127.0.0.1 localstack
	
We should set the startup order for calculation-operation service (Both MySQL and LocalStack should be UP before we start running this service)

- We can use either wait-for-it.sh or dockerize tool to make the calculation-operation service wait till MySQL server & Localstack Service for SQS is up.

- For introducing wait for a single service we can use wait-for-it.sh,

  bash ./wait-for-it.sh http://localhost:4566 --strict -- java -jar /calculator-operation-0.0.1-SNAPSHOT.jar
  
- If we want to wait for multiple hosts,

  sh -c "dockerize -wait tcp://db:6034 -wait tcp://localstack:4566 -timeout 400s -wait-retry-interval 80s -- java -jar /calculator-operation-0.0.1-SNAPSHOT.jar"

Localstack Service

- Set HOSTNAME_EXTERNAL=localstack to serve the SQS under localstack host.

- The queue will be created with the URL, "QueueUrl": "http://localstack:4566/000000000000/calculation-queue" (for Localstack version 0.11.3 and above)
  if you are using Localstack version 0.11.2 and older, the queue URL will be, "QueueUrl": "http://localstack:4566/queue/calculation-queue" 

  use the latest Localstack version to avoid the "Host connection refused" error while connection to the queue.

## Sending Message to SQS

- Create the QueueMessageChannel with the queue URL. Then we construct the message to be sent with the MessageBuilder class.
- We invoke the send() method on the MessageChannel by specifying a timeout interval. The send() method is a blocking call so it is always advisable to set a timeout when calling this method. Messages are created using the MessageBuilder helper class. 

	Message<String> msg = MessageBuilder.withPayload(messagePayload)
	 .setHeader("sender", "app1")
	 .setHeaderIfAbsent("country", "AE")
	 .build();
	
	...
	}

## Receiving Message from SQS

- We implement Long Polling using the @SQSListener annotation.
- We annotate a method with the @SqsListener annotation for subscribing to a queue. The @SqsListener annotation adds polling behavior to the method and also provides support for serializing and converting the received message to a Java object as shown below:

  @SqsListener(value = "testQueue", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
  public void receiveMessage(String message, 
    @Header("SenderId") String senderId) {
    logger.info("message received {} {}",senderId,message);
  }
- Methods annotated with @SqsListener can take both string and complex objects. For receiving complex objects, we need to configure a custom converter.
- Define a QueueMessageHandlerFactory to allow Spring to use our custom message converter for deserializing the messages it receives in its listener method.
- To make the message conversion more robust in this case, the Jackson message converter needs to be configured with the strictContentTypeMatch property set to false.  

We can build and run all containers using the command 'docker-compose up --build'
