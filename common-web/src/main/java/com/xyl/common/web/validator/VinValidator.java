package com.xyl.common.web.validator;


import com.alpha.common.utils.FieldUtil;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by cw on 16-1-15.
 */
public class VinValidator implements ConstraintValidator<Vin, String> {
    boolean allowEmpty = false;

    @Override
    public void initialize(Vin vin) {
        allowEmpty = vin.allowEmpty();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return (allowEmpty && StringUtils.isEmpty(s)) || (StringUtils.isNotEmpty(s) && FieldUtil.regex(s, "^[a-zA-Z0-9]{17}$"));
//        return (allowEmpty && StringUtils.isEmpty(s)) || (StringUtils.isNotEmpty(s) && CheckVin.isNewVinLegal(s));
    }
}
