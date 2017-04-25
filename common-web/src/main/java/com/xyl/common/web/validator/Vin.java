package com.xyl.common.web.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

/**
 * Created by cw on 16-1-15.
 */
@Documented
@Constraint(validatedBy = VinValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface Vin {
    boolean allowEmpty() default true;

    String message() default "车架号非法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
