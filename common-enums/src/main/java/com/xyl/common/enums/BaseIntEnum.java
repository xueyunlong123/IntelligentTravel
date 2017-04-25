package com.xyl.common.enums;

import java.io.Serializable;

public interface BaseIntEnum extends Serializable{
    public final static long serialVersionUID = 1l;

    int getValue();
    String getName();
}
