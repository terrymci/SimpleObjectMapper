package com.terrymci.som.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.terrymci.som.SOMSqlOracle;

public class SOMSqlOracleTest
{
	
	@BeforeClass
	public static void setup()
	{
		SOMSqlOracle.setBooleanTrue("'T'");
		SOMSqlOracle.setBooleanFalse("'F'");
	}
	
	@Test
	public void testToSQLInsert()
	{
		Employee employee = new Employee("Terry", false, 123456, 78.9);
		String sql = SOMSqlOracle.toSQLInsert(employee, "EMPLOYEE", Employee.Field.values());
		assertTrue(sql.equals("INSERT INTO EMPLOYEE(NAME, TEMPORARY, NUMBER, RATE) VALUES('Terry', 'F', 123456, 78.9);"));
		Employee.Field partialFields[] = 
			{
				Employee.Field.NUMBER,
				Employee.Field.RATE
			};
		sql = SOMSqlOracle.toSQLInsert(employee, "EMPLOYEE", partialFields);
		assertTrue(sql.equals("INSERT INTO EMPLOYEE(NUMBER, RATE) VALUES(123456, 78.9);"));
	}

	@Test
	public void testToSQLUpdate()
	{
		Employee employee = new Employee("Terry", true, 123456, 78.9);
		String sql = SOMSqlOracle.toSQLUpdate(employee, "EMPLOYEE", Employee.SQL_FIELDS_DATA, Employee.SQL_FIELDS_IDENTITY);
		assertTrue(sql.equals("UPDATE EMPLOYEE SET NAME = 'Terry', TEMPORARY = 'T', RATE = 78.9 WHERE NUMBER = 123456;"));
	}

}

