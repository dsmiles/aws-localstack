This document is a work in progress.

Additional content will be added as the project progresses.

This project is inspired by my previous experience working on a couple of Spring Boot AWS microservices at a former employer:

- UBO Service (Ultimate Beneficial Owner)
- Metrics Service (Case Management Metrics)

These services utilized AWS SQS, DynamoDB, and S3 to function. Additionally, they interfaced with third-party REST APIs to gather supplementary data from sources such as Companies House, Creditsafe, Lexis Nexis, etc.

Messages were passed to the microservices via SQS, where listeners were configured to monitor various queues.

Upon receiving a message, it was processed, and the resulting data was stored in DynamoDB and S3. Subsequently, completion messages were dispatched to other queues to trigger further processing. In certain instances, a REST API call was made for notification purposes.

By leveraging LocalStack, I was able to conduct isolated testing of the microservices on my local machine without the need to deploy them to the QA environment. Once I was satisfied with the results, I would deploy the services to the QA environment for further testing confident in the knowledge that it would operate exactly the same.

This afforded me complete control over the microservices, the data, and the AWS services. Furthermore, it enabled simulation of various failure scenarios to test recovery mechanisms.

