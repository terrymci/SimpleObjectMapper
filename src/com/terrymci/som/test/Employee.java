package com.terrymci.som.test;

import com.terrymci.som.SOMField;

/**
 * Simple test POJO.
 *
 */
public class Employee
{
// ENUMS/CONSTANTS...
	// Field order for serialization: different than declared field order
	public enum Field
	{
		NAME,
		TEMPORARY,
		NUMBER,
		RATE,
	}

	// Data fields for SQL update statement
	public static final Employee.Field SQL_FIELDS_DATA[] = 
		{
			Employee.Field.NAME,
			Employee.Field.TEMPORARY,
			Employee.Field.RATE
		};
	
	// Identity fields for SQL update statement
	public static final Employee.Field SQL_FIELDS_IDENTITY[] = 
		{
			Employee.Field.NUMBER
		};
	
	
// METHODS...	
	public Employee() {}
	
	@Override
	public String toString()
	{
		String result = "Name=" + getName() +
			",Temporary=" + getTemporary() +
			",Number=" + getNumber() +
			",Rate=" + getRate();
		return result;
	}
	
	public Employee(String name, boolean temporary, int number, double rate) 
	{
		setName(name);
		setTemporary(temporary);
		setNumber(number);
		setRate(rate);
	}
	
	// Get/set
	public int getNumber()
	{
		return number_;
	}
	public void setNumber(int number)
	{
		this.number_ = number;
	}
	public String getName()
	{
		return name_;
	}
	public void setName(String name)
	{
		this.name_ = name;
	}
	public double getRate()
	{
		return rate_;
	}
	public void setRate(double rate)
	{
		this.rate_ = rate;
	}
	public boolean getTemporary()
	{
		return temporary_;
	}
	public void setTemporary(boolean temporary)
	{
		this.temporary_ = temporary;
	}
	public String getPerformance()
	{
		return performance_;
	}
	public void setPerformance(String performance)
	{
		this.performance_ = performance;
	}


// DATA...	
	@SOMField(name="NUMBER")
	private int number_;
	
	@SOMField(name="NAME")
	private String name_;
	
	@SOMField(name="RATE")
	private double rate_;
	
	@SOMField(name="TEMPORARY")
	private boolean temporary_;
	
	@SOMField(excluded=true)
	private String performance_;
}
