package bank.app.dao;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import bank.app.entities.Account;

@Repository
public class AccountDaoImpl implements AccountDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private Blob getBlob(MultipartFile image) throws IOException, SerialException, SQLException {
		byte[] byteArr = image.getBytes();
		Blob imageBlob = new SerialBlob(byteArr);
		return imageBlob;
	}

	@Override
	public int insertCreatedAccount(Account account) throws SQLException, IOException {
		// `account_number`, `ifsc_code`, `customer_id`, `balance`, `opened_date`,
		// `account_type_id`, `id_proof`

		Blob idProof = getBlob(account.getIdProof());

		String query = "INSERT INTO account "
				+ "(account_number, ifsc_code, customer_id, balance, opened_date, account_type_id, id_proof )"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)";

		return jdbcTemplate.update(query, account.getAccountNumber(), account.getIfscCode(), account.getCustomerId(),
				account.getBalance(), account.getOpenedDate(), account.getAccountTypeId(), idProof);
	}

	@Override
	public List<Account> getAccountsByCustomerId(int customerId) throws SQLException, IOException {
		String query = "SELECT * FROM account WHERE customer_id=?";

		return jdbcTemplate.query(query, new AccountRowMapper(), customerId);
	}

}
