#Commands to be done in the terminal, using aws cli to configure the application

aws --endpoint http://localhost:4566 --profile localstack sqs create-queue --queue-name sqsSpringRestApi

aws --endpoint http://localhost:4566 --profile localstack sqs send-message --queue-url=http://localhost:4566/000000000000/sqsSpringRestApi --message-body "Test message"

aws --endpoint http://localhost:4566 --profile localstack sqs receive-message --queue-url http://localhost:4566/000000000000/sqsSpringRestApi
