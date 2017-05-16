package com.terrymci.som.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.terrymci.som.SOMMap;

public class SOMMapTest
{
	@Test
	public void testToMap()
	{
		Employee employee = new Employee("Terry", true, 123456, 78.9);
		Map<String, String> map = SOMMap.toMap(employee);
		assertTrue(map.get("NAME").equals("Terry"));
		assertTrue(map.get("TEMPORARY").equals("true"));
		assertTrue(map.get("NUMBER").equals("123456"));
		assertTrue(map.get("RATE").equals("78.9"));
	}

	@Test
	public void testFromMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("NAME", "Bella");
		map.put("TEMPORARY", "false");
		map.put("NUMBER", "234567");
		map.put("RATE", "87.65");
		Employee employee = new Employee();
		SOMMap.fromMap(employee, map);
		assertTrue(employee.getName().equals("Bella"));
		assertTrue(employee.getNumber() == 234567);
		assertTrue(employee.getTemporary() == false);
		assertTrue(employee.getRate() == 87.65);
	}
}
