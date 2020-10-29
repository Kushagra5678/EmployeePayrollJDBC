package com.practice.employeepayroll;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import java.sql.Connection;
import com.mysql.jdbc.Driver;
import java.sql.PreparedStatement;

public class DBDemo {
	public static void main(String[] args) {

		String jdbcURL = "jdbc:mysql://localhost:3306/pay_roll_service";
		String userName = "kushagra";
		String password = "admin1";
		Connection connection = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("cannot find the driver in the classpath!", e);
		}

		try {
			System.out.println("Connecting to database" + jdbcURL);
			connection = DriverManager.getConnection(jdbcURL, userName, password);
			System.out.println("Connected successfully " + connection);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
