package com.terrymci.som;

import java.util.HashMap;
import java.util.Map;
import java.lang.Enum;

/**
 * Static-only class to convert between simpe objects and comma-separated-value (CSV) text
 *
 */
public class SOMCsv
{
	/**
	 * Convert data field map to CSV text line.
	 * @param fieldMap - data field map
	 * @param fieldNames - enumeration of field names that defines the mapped fields included and their sequence
	 * @return - CSV text 
	 */
    public static <T extends Enum<T>> String toCSV(Map<String, String> fieldMap, T[] fieldNames)
    {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (T fieldNameEnum : fieldNames)
        {
            if (count > 0)
            {
                sb.append(",");
            }
            ++count;
            sb.append(fieldMap.get(fieldNameEnum.toString()));
        }
        
        return sb.toString();
    }

	/**
	 * Converts a simple object to CSV text.
	 * @param obj - object to convert
	 * @param fieldNames - enumeration of field names that defines the mapped fields included and their sequence
	 * @return
	 */
    public static <T extends Enum<T>> String toCSV(Object obj, T[] fieldNames)
    {
        Map<String, String> fieldMap = SOMMap.toMap(obj);
        return toCSV(fieldMap, fieldNames);
    }

    /**
     * Convert a CSV string to a data field map.
     * @param lineValues - CSV string input
	 * @param fieldNames - enumeration of field names that defines the mapped fields included and their sequence     * @return
     */
    public static <T extends Enum<T>> Map<String, String> fromCSV(String lineValues, T[] fieldNames)
    {
        Map<String, String> fieldMap = new HashMap<String, String>();
        String fieldValues[] = lineValues.split(",");
        int countValues = Math.min(fieldNames.length, fieldValues.length);
        int index = 0;
        for (T fieldNameEnum : fieldNames)
        {
            if (index < countValues)
            {
                fieldMap.put(fieldNameEnum.toString(), fieldValues[index]);
            }
            ++index;
        }
        
        return fieldMap;
    }

    /**
     * Convert CSV text into a simple object.
     * @param obj - object to set up
     * @param lineValues - CSV text input
	 * @param fieldNames - enumeration of field names that defines the mapped fields included and their sequence    
     */
    public static <T extends Enum<T>> void fromCSV(Object obj, String lineValues, T[] fieldNames)
    {
        Map<String, String> fieldMap = fromCSV(lineValues, fieldNames);
        SOMMap.fromMap(obj, fieldMap);
    }
    
    /**
     * Convert CSV value text and CSV field listing text into data field map.
     * @param lineValues - CSV input text with field values
     * @param lineFields - CSV input text with field names
     * @return
     */
    public static Map<String, String> fromCSV(String lineValues, String lineFields)
    {
        Map<String, String> fieldMap = new HashMap<String, String>();
        String fieldNames[] = lineFields.split(",");
        String fieldValues[] = lineValues.split(",");
        int count = Math.min(fieldNames.length, fieldValues.length);
        for (int index = 0; index < count; ++index)
        {
           fieldMap.put(fieldNames[index], fieldValues[index]); 
        }
        
        return fieldMap;
    }

    /**
     * Convert CSV value text and CSV field listing text into simple object.
     * @param obj - object to set up 
     * @param lineValues - CSV input text with field values
     * @param lineFields - CSV input text with field names
     */
    public static void fromCSV(Object obj, String lineValues, String lineFields)
    {
        Map<String, String> fieldMap = fromCSV(lineValues, lineFields);
        SOMMap.fromMap(obj, fieldMap);
    }
    
}
