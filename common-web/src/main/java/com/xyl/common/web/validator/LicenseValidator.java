package com.xyl.common.web.validator;


import com.alpha.common.utils.FieldUtil;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by cw on 16-1-15.
 */
public class LicenseValidator implements ConstraintValidator<LicenseNo, String> {
    boolean allowEmpty = false;

    @Override
    public void initialize(LicenseNo licenseNo) {
        allowEmpty = licenseNo.allowEmpty();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return (allowEmpty && StringUtils.isEmpty(s)) || (StringUtils.isNotEmpty(s) && FieldUtil.regex(s, "^[京,津,渝,沪,冀,晋,辽,吉,黑,苏,浙,皖,闽,赣,鲁,豫,鄂,湘,粤,琼,川,贵,云,陕,秦,甘,陇,青,台,内蒙古,桂,宁,新,藏,澳,军,海,航,警][A-Z][0-9,A-Z]{5}$"));
    }
}
