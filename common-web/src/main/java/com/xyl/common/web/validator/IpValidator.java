package com.xyl.common.web.validator;


import com.alpha.common.utils.FieldUtil;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by cw on 16-1-15.
 */
public class IpValidator implements ConstraintValidator<Ip, String> {
    boolean allowEmpty = false;

    @Override
    public void initialize(Ip licenseNo) {
        allowEmpty = licenseNo.allowEmpty();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return (allowEmpty && StringUtils.isEmpty(s)) || (StringUtils.isNotEmpty(s) && FieldUtil.regex(s, "^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$"));
    }

    public static void main(String[] args) {
        System.out.println(FieldUtil.regex("190.0.0.1", "^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$"));
    }
}
