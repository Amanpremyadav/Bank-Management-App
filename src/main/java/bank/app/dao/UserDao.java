package bank.app.dao;

import java.io.IOException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialException;

import bank.app.entities.Branch;
import bank.app.entities.Roles;
import bank.app.entities.User;

public interface UserDao {

	int insertUser(User user) throws SQLException, IOException;

	List<Roles> fetchAllRoles() throws SQLException, IOException;

	List<Branch> fetchAllBranch() throws SQLException, IOException;
	
	List<User> fetchAllDetails(String username) throws SQLException, IOException;

	void insertEmployee(int userId, int branchId);

	void insertCustomer(int userId, int branchId);

	void insertBankManager(int userId, int branchId);

	Map<String, Object> fetchPwds(String username);
	
	User modifyUser(User user) throws SerialException, IOException, SQLException;
	
	User updatePassword(String HashPassword, User user) throws SerialException, IOException, SQLException;


}
