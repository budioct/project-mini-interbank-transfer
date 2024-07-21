# Bank Transfer Application

Welcome to the Bank Transfer Application, a Spring Boot-based service designed to facilitate inter-bank transfers using Java concurrency mechanisms. This documentation provides a detailed overview of the application, including its features, program structure, and how to interact with it via JSON requests and responses.

## Features

- **Inter-Bank Transfer**: Supports transfers between different banks.
- **Concurrent Processing**: Uses Java threads for concurrent transfer processing.
- **Transaction Management**: Ensures atomicity and consistency using Spring's transaction management.
- **Validation**: Validates transfer requests to ensure data integrity.

## Program Description

The Bank Transfer Application is built using Spring Boot 3. The application comprises the following components:

- **Entity**: Represents the `Account` entity stored in the database.
- **Repository**: Provides CRUD operations for the `Account` entity.
- **Service**: Contains the business logic for performing bank transfers.
- **Service Implementation**: Implements the `AccountService` interface, using Java concurrency to handle transfers.
- **REST Controller**: Exposes an endpoint for initiating transfers.

# Database.sql Description

The `Database.sql` file is a SQL script designed to set up and populate the initial database schema for the Bank Transfer Application. This script will create the necessary database and table, and insert sample data to get you started.

## JSON Request

```
{
    "from_bank" : "MANDIRI",
    "from_account" : "1111122222",
    "to_bank" : "BCA",
    "to_account" : "1111133333",
    "amount" : "1000.00"
}
```

## JSON Response

```
{
    "status_code": 200,
    "message": "The transfer was created successfully",
    "data": {
        "from_bank": "MANDIRI",
        "from_account": "1111122222",
        "balance_from_sender": 7000.00,
        "to_bank": "BCA",
        "to_account": "1111133333",
        "balance_from_recipient": 13000.00
    }
}
```

## Bad Request
1
```
{
    "from_bank": "",
    "from_account": "1111133333",
    "to_bank": "MANDIRI",
    "to_account": "1111122222",
    "amount": 1000.00
}
```
2
```
{
    "from_bank" : "MANDIRI",
    "from_account" : "",
    "to_bank" : "BCA",
    "to_account" : "1111133333",
    "amount" : "1000.00"
}
```
3
```
{
    "from_bank" : "MANDIRI FALSE",
    "from_account" : "1111122222",
    "to_bank" : "BCA",
    "to_account" : "1111133333",
    "amount" : "1000.00"
}
```
4
```
{
"from_bank" : "MANDIRI",
"from_account" : "1111122222",
"to_bank" : "BCA",
"to_account" : "1111133333",
"amount" : "10000000000.0000"
}
```

## Response Exception 
1
```
{
    "status_code": 400,
    "message": "Validation errors in your request",
    "errors": "fromBank: must not be blank"
}
```
2
```
{
    "status_code": 400,
    "message": "Validation errors in your request",
    "errors": "fromAccount: must not be blank, fromAccount: account number must be 10 digits"
}
```
3
```
{
    "status_code": 404,
    "message": "The request was not valid",
    "errors": "Account not found"
}
```
4
```
{
    "status_code": 400,
    "message": "Validation errors in your request",
    "errors": "amount: numeric value out of bounds (<10 digits>.<2 digits> expected)"
}
```

## Acknowledgements

We hope this documentation helps you understand and use the Bank Transfer Application effectively.