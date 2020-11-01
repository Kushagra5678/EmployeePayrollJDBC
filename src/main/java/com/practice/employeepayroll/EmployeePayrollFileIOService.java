package com.practice.employeepayroll;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class EmployeePayrollFileIOService {
	public static String PAYROLL_FILE_NAME = "PayrollFile.txt";

	public void writeDataInFile(List<EmployeePayroll> employeePayrollList) {
		StringBuffer empBuff = new StringBuffer();
		for (EmployeePayroll e : employeePayrollList) {
			empBuff.append(e.toString() + "\n");
		}
		try {
			//Files.write(empBuff.toString().getBytes(), Paths.get(PAYROLL_FILE_NAME).toFile());
			Files.write(Paths.get(PAYROLL_FILE_NAME), empBuff.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public long countEntriesFromFile() {
		long lines = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(PAYROLL_FILE_NAME));
			while (reader.readLine() != null) {
				lines++;
			}
			reader.close();
		} catch (IOException e) {
		}
		return lines;

	}
}
