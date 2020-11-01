package com.practice.employeepayroll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {
	public List<EmployeePayroll> readData() {
		String sql = "Select * from emp_payroll;";
		List<EmployeePayroll> employeePayrollList = new ArrayList<>();
		try {
			Connection connection = this.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while(result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				double salary = result.getDouble("Salary");
				LocalDate start = result.getDate("Start").toLocalDate();
				employeePayrollList.add(new EmployeePayroll(id, name, salary, start));
				//System.out.println(employeePayrollList.size());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	
	private Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/pay_roll_service";
		String userName = "kushagra";
		String password = "admin1";
		Connection con = null;
		System.out.println("Connecting to database" + jdbcURL);
		con = DriverManager.getConnection(jdbcURL, userName, password);
		System.out.println("Connected successfully " + con);
		return con;
	}
}
