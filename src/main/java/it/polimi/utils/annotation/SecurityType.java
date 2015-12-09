package it.polimi.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SecurityType {
	it.polimi.model.SecurityType type() default it.polimi.model.SecurityType.ACCESS_WITH_PERMISSION;
}
