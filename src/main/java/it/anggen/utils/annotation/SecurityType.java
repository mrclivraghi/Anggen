package it.anggen.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SecurityType {
	it.anggen.model.SecurityType type() default it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION;
}
