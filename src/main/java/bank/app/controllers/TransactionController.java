package bank.app.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import bank.app.dao.AccountDaoImpl;
import bank.app.dao.TransactionDaoImpl;
import bank.app.entities.Account;
import bank.app.entities.User;
import bank.app.utility.Password;
import jakarta.servlet.http.HttpSession;

@Controller
public class TransactionController {

	@Autowired
	private AccountDaoImpl accountDaoImpl;

	@Autowired
	private TransactionDaoImpl transactionDaoImpl;

	@Autowired
	private HttpSession session;

	private List<Account> accounts;
	private int transactionTypeId;
	private double updatedBalance;
	private String resultMessage ;

	@GetMapping("/deposit")
	public String showDepositForm() {
		User user = (User) session.getAttribute("userDetails");
		try {
			accounts = accountDaoImpl.getAccountsByCustomerId(user.getUserId());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		session.setAttribute("accounts", accounts);

		return "customer/deposit";
	}

	@GetMapping("/withdraw")
	public String showWithdrawForm() {

		User user = (User) session.getAttribute("userDetails");
		try {
			accounts = accountDaoImpl.getAccountsByCustomerId(user.getUserId());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		session.setAttribute("accounts", accounts);

		return "customer/withdraw";
	}

	@PostMapping("/transaction")
	public String processTransaction(@RequestParam("accountNumber") String accountNumber,
			@RequestParam("transactionTime") String transactionTime,
			@RequestParam("transactionType") String transactionType, @RequestParam("amount") Double amount,
			@RequestParam("password") String password, RedirectAttributes attributes) {

		if (transactionType.equalsIgnoreCase("Deposit")) {
			transactionTypeId = 1;
		} else {
			transactionTypeId = 2;
		}

		User user = (User) session.getAttribute("userDetails");
		
		String pwdSalt = user.getPasswordSalt();
		String oldPwdHash = user.getPasswordHashed();

		// check password
		String newPassword = pwdSalt + password;
		String newPasswordHash = Password.generatePwdHash(newPassword);

		if (newPasswordHash.equals(oldPwdHash)) {
			// Perform the deposit or withdraw operation
			// Get updated balance

			try {
				resultMessage = transactionDaoImpl.saveTransaction(accountNumber, amount, transactionTypeId);
				updatedBalance = transactionDaoImpl.getAccountBalance(accountNumber);

				attributes.addFlashAttribute("message", resultMessage);
			} catch (Exception e) {
				attributes.addFlashAttribute("message", "An error occured while processing transaction.");
			}
		} else {
			attributes.addFlashAttribute("message", "Password is incorrect");
			return transactionTypeId == 1 ? "redirect:/deposit" : "redirect:/withdraw";
		}

		session.setAttribute("updatedBalance", updatedBalance);

		return resultMessage.equalsIgnoreCase("Transaction successful.")?"customer/transactionSuccess":(transactionTypeId == 1 ? "redirect:/deposit" : "redirect:/withdraw");
		
	}
}
