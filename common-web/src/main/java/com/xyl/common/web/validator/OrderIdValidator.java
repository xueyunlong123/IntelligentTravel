package com.xyl.common.web.validator;


import com.alpha.common.utils.BusinessUtil;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by cw on 16-1-15.
 */
public class OrderIdValidator implements ConstraintValidator<OrderId, String> {
    boolean allowEmpty = false;

    @Override
    public void initialize(OrderId applyId) {
        allowEmpty = applyId.allowEmpty();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return (allowEmpty && StringUtils.isEmpty(s)) || (StringUtils.isNotEmpty(s) && BusinessUtil.APPLY_ID.validate(s));
    }
}
