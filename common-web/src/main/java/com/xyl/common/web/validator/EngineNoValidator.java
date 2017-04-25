package com.xyl.common.web.validator;


import com.alpha.common.utils.FieldUtil;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by cw on 16-1-15.
 */
public class EngineNoValidator implements ConstraintValidator<EngineNo, String> {
    boolean allowEmpty = false;

    @Override
    public void initialize(EngineNo vin) {
        allowEmpty = vin.allowEmpty();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return (allowEmpty && StringUtils.isEmpty(s)) || (StringUtils.isNotEmpty(s) && FieldUtil.regex(s, "^[0-9A-Z]+$"));
    }
}
