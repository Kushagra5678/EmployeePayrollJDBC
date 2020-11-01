package com.practice.employeepayroll;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollService {
	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}

	private List<EmployeePayroll> employeePayrollList;
	private EmployeePayrollDBService employeePayrollDBService;
	
	public EmployeePayrollService() {
		// TODO Auto-generated constructor stub
		employeePayrollDBService = EmployeePayrollDBService.getInstance();
	}

	public EmployeePayrollService(List<EmployeePayroll> employeePayrollList) {
		this();
		this.employeePayrollList = employeePayrollList;
	}
	
	public List<EmployeePayroll> readEmployeePayrollData(IOService ioService) throws SQLException{
		if(ioService.equals(IOService.DB_IO))
			this.employeePayrollList = employeePayrollDBService.readData();
		return this.employeePayrollList;
	}

	public void readData(Scanner sc) {
		System.out.println("Enter Employee ID : ");
		int id = sc.nextInt();
		System.out.println("Enter Employee Name : ");
		String name = sc.next();
		System.out.println("Enter Employee Salary : ");
		double salary = sc.nextDouble();
		employeePayrollList.add(new EmployeePayroll(id, name, salary));
	}

	public void writeData(IOService iOService) {
		if(iOService.equals(IOService.CONSOLE_IO)) {
		System.out.println("Writing Employee Payroll Details To The Console : ");
		System.out.println(employeePayrollList);
		}
		else if(iOService.equals(IOService.FILE_IO))
		{
			new EmployeePayrollFileIOService().writeDataInFile(employeePayrollList);
		}
	}

	public static void main(String[] args) {
		ArrayList<EmployeePayroll> empdetailslist = new ArrayList<>();
		EmployeePayrollService EPS = new EmployeePayrollService(empdetailslist);
		Scanner sc = new Scanner(System.in);
		EPS.readData(sc);
		
	}

	public long countEntries(IOService fileIo) {
		if(fileIo.equals(IOService.CONSOLE_IO))
		{
			return employeePayrollList.size();
		}
		else if(fileIo.equals(IOService.FILE_IO))
		{
			return new EmployeePayrollFileIOService().countEntriesFromFile();
		}
		else return 0;
	}

	public void updateEmployeeSalary(String name, double salary) {
		// TODO Auto-generated method stub
		int result = employeePayrollDBService.updateEmployeeData(name, salary);
		if(result == 0) return;
		EmployeePayroll employeePayroll = this.getEmployeePayrollData(name);
		if(employeePayroll != null) {
			employeePayroll.salary = salary;
		}
	}

	private EmployeePayroll getEmployeePayrollData(String name)  {
		// TODO Auto-generated method stub
		EmployeePayroll ep;
		ep = this.employeePayrollList.stream()
				.filter(e -> e.getName().equals(name))
				.findFirst()
				.orElse(employeePayrollList.get(1));
		System.out.println(employeePayrollList.get(1));
		return ep;
	}

	public boolean checkEmployeePayrollInSyncWithDB(String name){
		// TODO Auto-generated method stub
		List<EmployeePayroll> employeePayrollList = employeePayrollDBService.getEmployeePayrollData(name);
		boolean ans =  employeePayrollList.get(0).equals(getEmployeePayrollData(name));
		return ans;
	}
}
