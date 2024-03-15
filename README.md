# Integration Testing AWS Services with LocalStack and TestContainers

This repository contains a Spring Cloud for Amazon Web Services project that demonstrates how to use LocalStack and TestContainers to perform integration testing of AWS services. 

This example uses AWS Simple Queue Service (SQS) to pass messages to a message handler listening on the queue, and then passing it to AWS Simple Storage Service (S3) to store the data.

This example project will be expanded to add more AWS services and demonstrate how to use LocalStack and TestContainers to perform integration testing of AWS services.

## Prerequisites
The following components are required to run the example:

- Java 17
- JUnit 5
- Spring Cloud for AWS
- Maven
- Docker Desktop 4.27.2
- Testcontainers
- Testcontainers JUnit 5 Extension
- Localstack Docker image
- Lombok
- Awaitility (asynchronous systems testing)

## Usage

To run the test framework, follow these steps:

1. Clone the repository:
```
git clone https://github.com/dsmiles/aws-localstack.git
cd aws-localstack
```

2. Run Maven test command:
```
mvn test
```