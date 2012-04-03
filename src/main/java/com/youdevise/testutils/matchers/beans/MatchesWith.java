package com.youdevise.testutils.matchers.beans;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchesWith {
    Class<? extends JavaBeanPropertyMatcherMaker<?>> value();
}
