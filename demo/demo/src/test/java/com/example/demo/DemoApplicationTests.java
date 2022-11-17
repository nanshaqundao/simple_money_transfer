package com.example.demo;

import com.example.demo.model.BankUser;
import com.example.demo.service.TransactionService;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DemoApplicationTests {
	@Autowired
	private TransactionService transactionService;

	@Autowired
	private UserService userService;

	@Test
	void checkTheBalanceIsCorrectAfterTransfer() throws Exception {
		BankUser sourceBankUser = userService.selectByUserName("Jack");
		BankUser targetBankUser = userService.selectByUserName("Tom");

		Double amount = 100.00;
		transactionService.transferMoney(sourceBankUser.getUserName(),amount, targetBankUser.getUserName(), sourceBankUser.getToken());
		Double expectedSourceBalance = sourceBankUser.getBalance() - amount;
		Double expectedTargetBalance = targetBankUser.getBalance() + amount;

		BankUser newSourceBankUser = userService.selectByUserName(sourceBankUser.getUserName());
		BankUser newTargetBankUser = userService.selectByUserName(targetBankUser.getUserName());


		assertEquals(expectedSourceBalance, newSourceBankUser.getBalance());
		assertEquals(expectedTargetBalance, newTargetBankUser.getBalance());
	}
}
