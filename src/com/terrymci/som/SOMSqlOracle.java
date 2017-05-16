package com.terrymci.som;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Class for generating Oracle-style SQL statements from simple objects
 *
 */
public class SOMSqlOracle
{
	// Methods to get/set the SQL value representation of boolean values
	//   (since there is no standard way in Oracle)
    public static String getBooleanTrue()
    {
        return booleanTrue_;
    }
    public static void setBooleanTrue(String booleanTrue)
    {
        booleanTrue_ = booleanTrue;
    }
    public static String getBooleanFalse()
    {
        return booleanFalse_;
    }
    public static void setBooleanFalse(String booleanFalse)
    {
        booleanFalse_ = booleanFalse;
    }
    
    
    /**
     * Generates a SQL INSERT statement from a simple object.
     * @param obj - Object to convert
     * @param tableName - SQL table name
     * @param fieldNamesValue - enumeration of fields to use in SQL
     */
    public static <T extends Enum<T>> String toSQLInsert
        (
        Object obj, 
        String tableName, 
        T[] fieldNamesValue
        )
    {
        Map<String, String> fieldMap = SOMMap.toMap(obj);
        
        StringBuilder sbColumns = new StringBuilder();
        sbColumns.append(OPEN);
        StringBuilder sbValues = new StringBuilder();
        sbValues.append(OPEN);
        int index = 0;
        for (T fieldNameEnum : fieldNamesValue)
        {
            if (index > 0)
            {
                sbColumns.append(COMMA);
                sbValues.append(COMMA);
            }
            String fieldNameStr = fieldNameEnum.toString(); 
            sbColumns.append(fieldNameStr);

            Field field = SOMMap.getField(obj, fieldNameStr);
            String fieldValue = getFieldValue(field.getType(), fieldMap.get(fieldNameStr));
            sbValues.append(fieldValue);
            ++index;
        }
        sbColumns.append(CLOSE);
        sbValues.append(CLOSE);
        
        StringBuilder sbSQL = new StringBuilder();
        sbSQL.append("INSERT INTO ");
        sbSQL.append(tableName);
        sbSQL.append(sbColumns);
        sbSQL.append(" VALUES");
        sbSQL.append(sbValues);
        sbSQL.append(COMMAND_END);
        
        return sbSQL.toString();
    }

   
    /**
     * Generates a SQL UPDATE statement from a simple object.
     * @param obj - Object to convert
     * @param tableName - SQL table name
     * @param fieldNamesValue - enumeration of fields to use in SQL
     * @param fieldNamesValue - enumeration of fields that define the update record identity (typically the primary key(s))
     */
    public static <T extends Enum<T>> String toSQLUpdate
        (
        Object obj, 
        String tableName, 
        T[] fieldNamesValue,
        T[] fieldNamesIdentity
        )
    {
        Map<String, String> fieldMap = SOMMap.toMap(obj);
        StringBuilder sbSQL = new StringBuilder();
        sbSQL.append("UPDATE ");
        sbSQL.append(tableName);
        sbSQL.append(" SET ");
        int index = 0;
        for (T fieldName : fieldNamesValue)
        {
            String fieldNameStr = fieldName.toString();
            if (index > 0)
            {
                sbSQL.append(COMMA);
            }
            sbSQL.append(fieldNameStr);
            sbSQL.append(EQUALS);

            Field field = SOMMap.getField(obj, fieldNameStr);
            String fieldValue = getFieldValue(field.getType(), fieldMap.get(fieldNameStr));
            sbSQL.append(fieldValue);
            ++index;
        }

        if (fieldNamesIdentity.length > 0)
        {
            sbSQL.append(" WHERE ");
            index = 0;
            for (T fieldNameEnum : fieldNamesIdentity)
            {
                String fieldNameStr = fieldNameEnum.toString();
                if (index > 0)
                {
                    sbSQL.append(AND);
                }
                sbSQL.append(fieldNameStr);
                sbSQL.append(EQUALS);
    
                Field field = SOMMap.getField(obj, fieldNameStr);
                String fieldValue = getFieldValue(field.getType(), fieldMap.get(fieldNameStr));
                sbSQL.append(fieldValue);
                ++index;
            }
        }        
        
        sbSQL.append(COMMAND_END);
        
        return sbSQL.toString();
    }

    /**
     * Returns a field value for use in SQL. Applies SQL quotes for string
     *   values, converts boolean values to configured representation, etc.
     * @param fieldType - Java type of field to represent in SQL
     * @param fieldValueRaw - Raw string value of field
     * @return
     */
    private static String getFieldValue(Class fieldType, String fieldValueRaw)
    {
        StringBuilder result = new StringBuilder();
        // Use quotes if this is a string type
        boolean needQuotes = (fieldType == String.class);
        if (needQuotes)
        {
            result.append(QUOTE);
        }
        // Booleans - used class-configured "true"/"false" representations
        if ((fieldType == Boolean.class) ||
            (fieldType == boolean.class))
        {
            if (fieldValueRaw == STRING_TRUE)
            {
                result.append(booleanTrue_);
            }
            else
            {
                result.append(booleanFalse_);
            }
        }
        else
        {
            result.append(fieldValueRaw);
        }
        if (needQuotes)
        {
            result.append(QUOTE);
        }
        return result.toString();
    }

// CONSTANTS...    
    private static String booleanTrue_ = "1";
    private static String booleanFalse_ = "0";
    
    private static final String STRING_TRUE = "true";
    private static final String COMMA = ", ";
    private static final String AND = "AND ";
    private static final String EQUALS = " = ";
    private static final String QUOTE = "'";
    private static final String OPEN = "(";
    private static final String CLOSE = ")";
    private static final String COMMAND_END = ";";
}
