package com.example.demo;

import com.example.demo.model.User;
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
		User sourceUser = userService.selectByUserName("Jack");
		User targetUser = userService.selectByUserName("Tom");

		Double amount = 100.00;
		transactionService.transferMoney(sourceUser.getUserName(),amount,targetUser.getUserName(),sourceUser.getToken());
		Double expectedSourceBalance = sourceUser.getBalance() - amount;
		Double expectedTargetBalance = targetUser.getBalance() + amount;

		User newSourceUser = userService.selectByUserName(sourceUser.getUserName());
		User newTargetUser = userService.selectByUserName(targetUser.getUserName());


		assertEquals(expectedSourceBalance,newSourceUser.getBalance());
		assertEquals(expectedTargetBalance,newTargetUser.getBalance());
	}
}
