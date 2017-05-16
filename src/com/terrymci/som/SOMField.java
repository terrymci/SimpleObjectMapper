package com.terrymci.som;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * 
 * Annotation type for SOM fields.
 *   name - field name to use for map keys
 *   excluded - set to true to exclude field from map
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SOMField {
    public String name() default "";
    public boolean excluded() default false;
}
