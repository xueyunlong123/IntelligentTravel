package com.xyl.common.web.validator;


import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by cw on 16-1-15.
 */
public class MessageDateValidator implements ConstraintValidator<MessageDate, String> {
    boolean allowEmpty = false;

    @Override
    public void initialize(MessageDate messageDate) {
        allowEmpty = messageDate.allowEmpty();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return (allowEmpty && StringUtils.isEmpty(s)) || (StringUtils.isNotEmpty(s) && s.matches("^\\d{4}\\-\\d{2}\\-\\d{2}$"));
    }
}
