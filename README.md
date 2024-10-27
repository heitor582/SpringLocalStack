# Description
A project with a single endpoint for simulate the use of sqs using clean arch and DDD basics,
the sample data initiates on the startup by flyway migrations

### Stack
- Java 21
- Spring Boot 3.3
- Spring Doc
- LocalStack
  - SQS
- PostgreSQL
- Flyway
- Spring Web
- TestContainers
- JPA

# Installation
## Running the app with docker
```bash
# Initiate the docker
$ docker-compose up --build -d
```
# Test

```bash
# Unit tests
$ ./gradlew test
```
# Executing OpenApi/Swagger
In the url after run the program access for enter the swagger and see all the routes with the respective parameters.
 ```bash
 http://localhost:8080/api/swagger-ui/index.html
```

Payment Scenarios

Partial and Total
```
{
    "seller_id": "123e4567e89b12d3a456426614174000",
    "payment_item": [
        {
            "charge_id": "223e4567e89b12d3a456426614174003",
            "amount": 50.00
        },
        {
            "charge_id": "223e4567e89b12d3a456426614174004",
            "amount": 150.00
        }
    ]
}
```

Surplus and Partial
```
{
    "seller_id": "123e4567e89b12d3a456426614174001",
    "payment_item": [
        {
            "charge_id": "223e4567e89b12d3a456426614174005",
            "amount": 201.00
        },
        {
            "charge_id": "223e4567e89b12d3a456426614174006",
            "amount": 25.00
        }
    ]
}
```

Total
```
{
    "seller_id": "123e4567e89b12d3a456426614174002",
    "payment_item": [
        {
            "charge_id": "223e4567e89b12d3a456426614174007",
            "amount": 75.00
        }
    ]
}
```
