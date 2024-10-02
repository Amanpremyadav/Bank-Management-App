package bank.app.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import bank.app.dao.UserDaoImpl;
import bank.app.entities.User;
import bank.app.utility.Password;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	UserDaoImpl userDaoImpl;
	@Autowired
	HttpSession session;

	@GetMapping("/openLoginPageCustomer")
	public String openLoginPageCustomer() {
		return "login";
	}

	@GetMapping("/openLoginPageBankEmp")
	public String openLoginPageBankEmp() {
		return "login";
	}

	@GetMapping("/openLoginPageBankMgr")
	public String openLoginPageBankMgr() {
		return "login";
	}

	@GetMapping("/openLoginPageRegionalMgr")
	public String openLoginPageRegionalMgr() {
		return "login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, Model model)
			throws SQLException, IOException {

		User userDetails = userDaoImpl.fetchAllDetails(username).get(0);

		System.out.println("\n login request data: " + username + ", " + password);

		Map<String, Object> userData = userDaoImpl.fetchPwds(username);
		String pwdSalt = (String) userData.get("salt_password");
		String oldPwdHash = (String) userData.get("hashed_password");

		System.out.println("pwdSalt :" + pwdSalt);
		System.out.println("oldPwdHash : " + oldPwdHash);

		// Check credentials
		String newPassword = pwdSalt + password;
		String newPasswordHash = Password.generatePwdHash(newPassword);

		if (newPasswordHash.equals(oldPwdHash)) {
			System.out.println("usersession : " + userDetails);

			if (userDetails != null) {
				session.setAttribute("userDetails", userDetails);

			} else {

				System.out.println("User details are null");
			}

			model.addAttribute("userData", userData);
			model.addAttribute("userName", username);
			model.addAttribute("userDetails", userDetails);

			int roleId = (Integer) userData.get("role_id");
			System.out.println(roleId);
			if (roleId == 1) {
				return "regional_mgr/regionalManagerDash";
			} else if (roleId == 2) {
				return "bank_mgr/bankManagerDash";
			} else if (roleId == 3) {
				return "bank_emp/bankEmployeeDash";
			} else if (roleId == 4) {
				return "customer/customerDash";
			}
		}

		return "login";
	}
}
