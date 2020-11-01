package com.practice.employeepayroll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {
	
	private PreparedStatement employeePayrollDataStatement;
	private static EmployeePayrollDBService employeePayrollDBService;
	private EmployeePayrollDBService() {
		
	}
	public static EmployeePayrollDBService getInstance() {
		if(employeePayrollDBService == null) {
			employeePayrollDBService = new EmployeePayrollDBService();
		}
		return employeePayrollDBService;
	}
	
	public List<EmployeePayroll> readData() {
		String sql = "Select * from emp_payroll;";
		List<EmployeePayroll> employeePayrollList = new ArrayList<>();
		try {
			Connection connection = this.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			employeePayrollList = this.getEmployeePayrollData(result);
			connection.close();
		}
		
		catch (SQLException e) {
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

	public int updateEmployeeData(String name, double salary) {
		// TODO Auto-generated method stub
		return this.updateEmplyeeDataUsingStatement(name, salary);
	}

	private int updateEmplyeeDataUsingStatement(String name, double salary) {
		// TODO Auto-generated method stub
		String sql = String.format("Update emp_payroll set salary = %.2f where name = '%s'", salary, name);
		try(Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public List<EmployeePayroll> getEmployeePayrollData(String name) {
		// TODO Auto-generated method stub
		List<EmployeePayroll> employeePayrollList = null;
		if(this.employeePayrollDataStatement == null)
			this.prepareStatementForEmployeeData();
		try {
			employeePayrollDataStatement.setString(1, name);
			ResultSet resultSet = employeePayrollDataStatement.executeQuery();
			employeePayrollList = this.getEmployeePayrollData(resultSet);
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	private List<EmployeePayroll> getEmployeePayrollData(ResultSet result) {
		// TODO Auto-generated method stub
		List<EmployeePayroll> employeePayrollList = new ArrayList<>();
		try {
			while(result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				double salary = result.getDouble("Salary");
				LocalDate start = result.getDate("Start").toLocalDate();
				employeePayrollList.add(new EmployeePayroll(id, name, salary, start));
				//System.out.println(employeePayrollList.size());
				
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	private void prepareStatementForEmployeeData() {
		// TODO Auto-generated method stub
		try {
			Connection con = this.getConnection();
			String sql = "Select * from emp_payroll where name = ?";
			employeePayrollDataStatement = con.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
