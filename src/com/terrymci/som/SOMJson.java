package com.terrymci.som;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class for generating JSON representation of simple objects 
 */
public class SOMJson
{
/*
    {
      "myString" : "value1",
      "myInt" : 123,
      "myDouble" : 456.78
      "myBool" : true
    }
*/
    public static <T extends Enum<T>> List<String> toJSONLines(Object obj, T[] fieldNames)
    {
        Map<String, String> fieldMap = SOMMap.toMap(obj);
        List<String> lines = new ArrayList<String>(); 

        lines.add(OPEN);

        int index = 0;
        int count = fieldNames.length;
        for (T fieldNameEnum : fieldNames)
        {
            String fieldNameStr = fieldNameEnum.toString();
            String fieldValue = fieldMap.get(fieldNameStr);
            Field field = SOMMap.getField(obj, fieldNameStr);
            boolean needValueQuotes = (field.getType() == String.class);
            String fieldValueJson = fieldValue;
            if (needValueQuotes)
            {
                fieldValueJson = QUOTE + fieldValue + QUOTE; 
            }
            String line = INDENT + QUOTE + fieldNameStr + QUOTE + SEPARATOR + fieldValueJson;
            if (index < (count - 1))
            {
                line = line + COMMA;
            }
            lines.add(line);
            ++index;
        }
        
        lines.add(CLOSE);

        return lines;
    }

    public static <T extends Enum<T>> String toJSONString(Object obj, T[] fieldNames)
    {
        List<String> lines = toJSONLines(obj, fieldNames);
        StringBuilder sb = new StringBuilder();
        for (String line : lines)
        {
            sb.append(line);
            sb.append(LINE_FEED);
        }
        return sb.toString();
    }    

    // TO DO LATER: "fromJSON"
    
// CONSTANTS...    
    private static final String INDENT = "  ";
    private static final String QUOTE = "\"";
    private static final String SEPARATOR = " : ";
    private static final String COMMA = ",";
    private static final String OPEN = "{";
    private static final String CLOSE = "}";
    private static final String LINE_FEED = "\n";
}
