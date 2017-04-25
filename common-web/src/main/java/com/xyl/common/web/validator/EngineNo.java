package com.xyl.common.web.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

/**
 * Created by cw on 16-1-15.
 */
@Documented
@Constraint(validatedBy = EngineNoValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface EngineNo {
    boolean allowEmpty() default true;

    String message() default "车架号非法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
