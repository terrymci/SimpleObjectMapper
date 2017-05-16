package com.terrymci.som.test;

import com.terrymci.som.SOMCsv;
import com.terrymci.som.SOMJson;
import com.terrymci.som.SOMSqlOracle;

public class SimpleObjectMapperTestApp
{

	/**
	 * Sample usage test application.
	 * @param args
	 */
	public static void main(String[] args)
	{
		Employee employee1 = new Employee("Tiberius", true, 345678, 98.76);
		employee1.setPerformance("Needs improvement");
		String csv = SOMCsv.toCSV(employee1, Employee.Field.values());
		System.out.println("Employee 1 CSV: " + csv);
		
		Employee employee2 = new Employee();
		SOMCsv.fromCSV(employee2, csv, Employee.Field.values());
		System.out.println("Employee 2: " + employee2);
		
		String sql = SOMSqlOracle.toSQLInsert(employee1, "EMPLOYEE", Employee.Field.values());
		System.out.println("Employee 1 INSERT: " + sql);
		sql = SOMSqlOracle.toSQLUpdate(employee2, "EMPLOYEE", Employee.SQL_FIELDS_DATA, Employee.SQL_FIELDS_IDENTITY);
		System.out.println("Employee 2 UPDATE: " + sql);

		String json = SOMJson.toJSONString(employee1, Employee.Field.values());
		System.out.println("Employee 1 JSON:\n" + json);
	}

}
