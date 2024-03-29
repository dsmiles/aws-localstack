package com.github.dsmiles.awslocal;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.util.UUID;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS;

@Testcontainers
public class BaseContainersTest {

    private static final String LOCALSTACK_VERSION = "localstack/localstack:latest";
    private static final DockerImageName localstackImage = DockerImageName.parse(LOCALSTACK_VERSION);

    @Container
    static LocalStackContainer localStack = new LocalStackContainer(localstackImage);

    static final String BUCKET_NAME = UUID.randomUUID().toString();
    static final String QUEUE_NAME = UUID.randomUUID().toString();

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("app.bucket", () -> BUCKET_NAME);
        registry.add("app.queue", () -> QUEUE_NAME);
        registry.add("spring.cloud.aws.region.static", () -> localStack.getRegion() );
        registry.add("spring.cloud.aws.credentials.access-key", () -> localStack.getAccessKey() );
        registry.add("spring.cloud.aws.credentials.secret-key", () -> localStack.getSecretKey() );
        registry.add("spring.cloud.aws.s3.endpoint", () -> localStack.getEndpointOverride(S3).toString() );
        registry.add("spring.cloud.aws.sqs.endpoint", () -> localStack.getEndpointOverride(SQS).toString() );
    }

    // Could use these helpers to replace the lambda with a method reference
    private static String getS3Endpoint() {
        return localStack.getEndpointOverride(S3).toString();
    }

    private static String getSqsEndpoint() {
        return localStack.getEndpointOverride(SQS).toString();
    }

    // Configure the AWS services

    @BeforeAll
    static void beforeAll() throws IOException, InterruptedException {
        localStack.execInContainer("awslocal", "s3", "mb", "s3://" + BUCKET_NAME);
        localStack.execInContainer("awslocal", "sqs", "create-queue", "--queue-name", QUEUE_NAME);
    }

}
