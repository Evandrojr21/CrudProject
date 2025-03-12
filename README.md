# API REST with Spring boot and aws services.
[![My Skills](https://skillicons.dev/icons?i=java,docker,spring,aws,mysql,postman,git)](https://skillicons.dev)
 
## 1. Introduction 
 
Project developed to practice knowledge learned in Spring boot together with aws services, using localstack
 
### 1.1 Objective
Create a Spring Boot application that sends SQS messages to a queue and another application consumes these messages. And, the database credentials are stored in the secrets manager.
 
 
## 2. Requirements

- Docker 27.5.1
- Intellij community 2024.3 or the version of your preference
- Java 21 amazon-correto
- Aws CLi 1.37.3
- Mysql 8.0.41
- Mysql Workbench 8.0.40
- git 2.43.0
 
## 3. Configuration to run the project
With everything downloaded and configured we can move on to the next steps:
 
```sh
git clone https://github.com/Evandrojr21/CrudProject
 
cd CrudProject
```
 
### 1. Create the database
In MySql workbench create the database with the name of your preference and save the url of this database
 
### 2. Up the docker container for localstack
Open the terminal, move to the folder corresponding to the project and go to the localstack subfolder and use the command:
 
```sh
docker compose up
```
 
Check that no errors have been displayed on the terminal to continue.
 
### 3. Create The secrets in the Secrets manager.
Open another terminal and run the following commands:
 
```bash
# Create Secrets
 
# Url Secret
aws --endpoint-url=http://localhost:4566 --profile localstack secretsmanager create-secret  --name dbUrl --secret-string "jdbc:MySql://localhost:3306/YourDatabase"  --region us-east-1
 
# Username secret
aws --endpoint-url=http://localhost:4566 --profile localstack secretsmanager create-secret  --name dbUsername  --secret-string "yourUsername" --region us-east-1
 
# Password secret
aws --endpoint-url=http://localhost:4566 --profile localstack secretsmanager create-secret  --name dbPassword  --secret-string "yourPassword"  --region us-east-1
```
### 4. Create the Sqs queue
In the terminal run the following command to create the queue:
```bash
aws --endpoint http://localhost:4566 --profile localstack sqs create-queue --queue-name sqsSpringRestApi
```

### 5. Run the application and send requests
You will run the application and send the requests to the **_http://localhost:8080/students_**,
example of body to post request:
```json
{       
    "name": "John",
    "percentage": 77.0,
    "branch": "IT"
}
```
## 4. Configure the Sqs listener application
Change branch main to listener branch and clone the repository in a empty folder:
```bash
git clone -b Listener https://github.com/Evandrojr21/CrudProject
```
open the project in Intellij and you will be able to run the application and see the messages being consumed as you send requests through the other application.
