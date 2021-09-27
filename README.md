# Description

A simple demo of money transfer between account

## Usage

 - Perform maven package to build the jar file.
 - Run the Jar file


## Explanation

 - It is using JDK11 to compile and build.
 - It is using the simple H2 in memory database for the demo
 - The pre-load data are three user accounts "Jack", "Richard" and "Tom"

## API

 - GET - http://localhost:8080/selectByUserName?userName=Jack
 ```
 This is to see Account Jack's info, including the balance and token
 ```
 - GET - http://localhost:8080/listAllUsers
 ```
 This is to see all accounts information
 ```
 - POST - http://localhost:8080/transferMoney?sourceAccount=Jack&amount=100.00&targetAccount=Tom&token=jacky
 ```
 This is to transfer from Jack to Tom, an 100.00 amount of money using Jack's token
 ```
## H2 Database
### Connection
 - web console URL: http://localhost:8080/h2-console/
 - JDBC URL: jdbc:h2:mem:testdb
### Explanation
 - There are two tables, one for Account the other is for transaction records