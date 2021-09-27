# Description

A simple demo of money transfer between account

## Usage

Run the Jar file


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
