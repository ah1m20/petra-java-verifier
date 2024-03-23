package com.cognitionbox.petra.ast.terms;

import org.junit.runner.Runner;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Root {
    /**
     * @return a Runner class (must have a constructor that takes a single Class to run)
     */
    Class<?> value();
}
