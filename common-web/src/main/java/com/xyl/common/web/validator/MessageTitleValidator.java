package com.xyl.common.web.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by cw on 16-1-15.
 */
public class MessageTitleValidator implements ConstraintValidator<MessageTitle, String> {
    boolean allowEmpty = false;

    @Override
    public void initialize(MessageTitle messageTitle) {
        allowEmpty = messageTitle.allowEmpty();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && s.length() <= 20;
    }
}
