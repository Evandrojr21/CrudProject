#Commands to be done in the terminal, using aws cli to configure the application

aws --endpoint http://localhost:4566 --profile localstack sqs create-queue --queue-name sqsSpringRestApi

aws --endpoint http://localhost:4566 --profile localstack sqs send-message --queue-url=http://localhost:4566/000000000000/sqsSpringRestApi --message-body "Test message"

aws --endpoint http://localhost:4566 --profile localstack sqs receive-message --queue-url http://localhost:4566/000000000000/sqsSpringRestApi

# Create Secrets

#Url Secret
aws --endpoint-url=http://localhost:4566 --profile localstack secretsmanager create-secret  --name dbUrl --secret-string "jdbc:MySql://localhost:3306/YourDatabase"  --region us-east-1

#Username secret
aws --endpoint-url=http://localhost:4566 --profile localstack secretsmanager create-secret  --name dbUsername  --secret-string "yourUsername" --region us-east-1

aws --endpoint-url=http://localhost:4566 --profile localstack secretsmanager create-secret  --name dbPassword  --secret-string "yourPassword"  --region us-east-1