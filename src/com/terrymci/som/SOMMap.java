package com.terrymci.som;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/**
 * 
 * Static-only class to translate between simple objects and data field maps 
 *
 */
public class SOMMap
{
	/**
	 * Converts object to data fiekd map
	 * @param obj - object to serialize
	 * @return map of data fields to their values in string form
	 */
    static public Map<String, String> toMap(Object obj)
    {
        Map<String, String> result = new HashMap<String, String>();
        Class objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();
        for (Field field : fields)
        {
            field.setAccessible(true);
            String fieldName = field.getName();
            SOMField somField = field.getAnnotation(SOMField.class);
            if (somField != null)
            {
                if (somField.excluded())
                {
                    continue;
                }
                fieldName = somField.name();
            }
            try
            {
                String fieldValue = field.get(obj).toString();
                result.put(fieldName, fieldValue);
            } 
            catch (Exception e)
            {
                System.out.println("SimpleObjectMapper.toMap() exception, fieldName = " 
                    + fieldName + ", exception = " + e.getMessage());
            } 
        }
        
        return result;
    }

    /**
     * Converts data field map to object
     * @param obj - object to set up
     * @param fieldValues - source data field map
     */
    public static void fromMap(Object obj, Map<String, String> fieldValues)
    {
        for (String fieldName : fieldValues.keySet())
        {
            try
            {
                Field field = getField(obj, fieldName);
                if (field != null)
                {
                    field.setAccessible(true);
                    String fieldValue = fieldValues.get(fieldName);
                    Object value = stringToObject(fieldValue, field.getType());
                    field.set(obj, value);
                }
                else
                {
                    System.out.println("SimpleObjectMapper.fromMap(), could not find field: " + fieldName);
                }
            } 
            catch (Exception e)
            {
                System.out.println("SimpleObjectMapper.fromMap() exception, fieldName = " 
                        + fieldName + ", exception = " + e.getMessage());
            } 
        }
    }
    

    /**
     * Returns field for given field name - looks for match on Java
     *   data member name, then for match on SOMField annotation name
     * @param obj
     * @param fieldName
     * @return
     */
    public static Field getField(Object obj, String fieldName)
    {
        Class objClass = obj.getClass();
        Field field = null;
        try
        {
            field = objClass.getDeclaredField(fieldName);
        }
        catch (NoSuchFieldException e)
        {
            // Just swallow this and try look-up by annotation name
        }
        if (field == null)
        {
            field = findFieldByAnnotationName(objClass, fieldName);
        }
        
        return field;
    }

    /**
     * Returns the FIeld for the given SOMField annotation name, if one is present,
     *   else null.
     * 
     * @param objClass - source object
     * @param fieldName - target data member SOMField annotation field name 
     */
    private static Field findFieldByAnnotationName(Class objClass, String fieldName)
    {
        Field[] fields = objClass.getDeclaredFields();
        for (Field field : fields)
        {
            SOMField somField = field.getAnnotation(SOMField.class);
            if (somField != null)
            {
                if (!somField.excluded() && fieldName.equals(somField.name()))
                {
                    return field;
                }
            }
        }
        
        return null;
    }

    /**
     * Creates and returns an object for the given string value and field data type.
     *   Only supports Strings and Java built-in scalar types. Returned scalr types
     *   will be the "boxed" equivalent type (e.g. Integer)
     * @param valueStr - data value as a string
     * @param classType - object data type
     */
    private static Object stringToObject(String valueStr, Class classType)
    {
        Object valueObj = null;
        if (classType == String.class)
        {
            valueObj = valueStr;
        }
        else if (classType == boolean.class)
        {
            valueObj = new Boolean(valueStr);
        }
        else if (classType == char.class)
        {
            if (!valueStr.isEmpty())
            {
                valueObj = new Character(valueStr.charAt(0));
            }
        } 
        else if (classType == byte.class)
        {
            valueObj = new Byte(valueStr);
        }
        else if (classType == short.class)
        {
            valueObj = new Short(valueStr);
        }
        else if (classType == int.class)
        {
            valueObj = new Integer(valueStr);
        }
        else if (classType == long.class)
        {
            valueObj = new Long(valueStr);
        }
        else if (classType == float.class)
        {
            valueObj = new Float(valueStr);
        }
        else if (classType == double.class)
        {
            valueObj = new Double(valueStr);
        }
        else
        {
            System.out.println("SimpleObjectMapper.stringToObject(), unsupported object type: " +
                classType.toString());
        }
        
        return valueObj;
    }
    
}
