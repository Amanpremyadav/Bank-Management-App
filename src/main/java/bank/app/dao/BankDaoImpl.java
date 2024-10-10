package bank.app.dao;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BankDaoImpl implements BankDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getBranchIdByEmpId(int empUserId) throws SQLException, IOException {
		// `bank_employee`
		String sql = "SELECT branch_id FROM bank_employee WHERE be_id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] { empUserId }, Integer.class);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getbranchIdByMgrId(int mgrUserId) throws SQLException, IOException {
		// `bank_manager`
		String sql = "SELECT branch_id FROM bank_manager WHERE bm_id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] { mgrUserId }, Integer.class);
	}

}
