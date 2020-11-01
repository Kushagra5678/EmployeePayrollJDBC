package com.practice.employeepayroll;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.practice.employeepayroll.EmployeePayrollService.IOService;

public class PayrollTest {
	@Test
	public void given3EmployeesWhenWrittenToFilesShouldMatchTheGivenEntries() {
		ArrayList<EmployeePayroll> arrEmp = new ArrayList<EmployeePayroll>();
		arrEmp.add(new EmployeePayroll(1, "hi", 100000.87));
		arrEmp.add(new EmployeePayroll(2,"hello",43400.70));
		arrEmp.add(new EmployeePayroll(3,"kush",780000.50));
	EmployeePayrollService EPS=new EmployeePayrollService(arrEmp);
	EPS.writeData(IOService.FILE_IO);
	long count=EPS.countEntries(IOService.FILE_IO);
	//System.out.println(count);
	Assert.assertEquals(3,count);
	}
	
	@Test
	public void givenEmployeePayrollInDB_WhenRetrived_ShouldMatchEmployeeCount() throws SQLException {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayroll> employeePayroll = employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		//System.out.println(employeePayroll.size());
		Assert.assertEquals(3, employeePayroll.size());
	}
	
//	@Test
//	public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() throws SQLException {
//		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
//		List<EmployeePayroll> employeePayroll = employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
//		employeePayrollService.updateEmployeeSalary("Terisa", 3000000.00);
//		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
//		Assert.assertTrue(result);
//	}
	
	@Test
	public void givenNewSalaryForEmployee_WhenUpdatedPrepStmt_ShouldSyncWithDB() throws SQLException {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayroll> employeePayroll = employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.updateEmployeeSalary("Terisa", 3000000.00);
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
		Assert.assertTrue(result);
	}
}
