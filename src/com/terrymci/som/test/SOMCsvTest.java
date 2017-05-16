package com.terrymci.som.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.terrymci.som.SOMCsv;

public class SOMCsvTest
{

	@Test
	public void testToCSV()
	{
		Employee employee = new Employee("Bella",false,234567,87.65);
		employee.setPerformance("Sheds too much.");
		String csv = SOMCsv.toCSV(employee, Employee.Field.values());
		assertTrue(csv.equals("Bella,false,234567,87.65"));
		Employee.Field partialFields[] = 
			{
				Employee.Field.NUMBER,
				Employee.Field.RATE
			};
		csv = SOMCsv.toCSV(employee, partialFields);
		assertTrue(csv.equals("234567,87.65"));
	}

	@Test
	public void testFromCSV()
	{
		String csv = "Terry,true,123456,78.9";
		Employee employee = new Employee();
		SOMCsv.fromCSV(employee, csv, Employee.Field.values());
		assertTrue(employee.getName().equals("Terry"));
		assertTrue(employee.getNumber() == 123456);
		assertTrue(employee.getTemporary() == true);
		assertTrue(employee.getRate() == 78.9);
		assertTrue(employee.getPerformance() == null);
	}

	@Test
	public void testFromCSVWithFieldCSV()
	{
		String csvValues = "123456,Terry,true,78.9";
		String csvFields = "NUMBER,NAME,TEMPORARY,RATE";
		Employee employee = new Employee();
		SOMCsv.fromCSV(employee, csvValues, csvFields);
		assertTrue(employee.getName().equals("Terry"));
		assertTrue(employee.getNumber() == 123456);
		assertTrue(employee.getTemporary() == true);
		assertTrue(employee.getRate() == 78.9);
	}

}

