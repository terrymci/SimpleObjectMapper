package com.terrymci.som.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Unit test suite for project
 */
@RunWith(Suite.class)
@SuiteClasses({
   SOMMapTest.class,
   SOMCsvTest.class,
   SOMSqlOracleTest.class,
   })
public class SimpleObjectMapperTest
{

}

