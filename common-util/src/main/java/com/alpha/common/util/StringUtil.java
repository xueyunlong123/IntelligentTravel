package com.alpha.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xueyunlong on 17-4-18.
 */
public class StringUtil {
    public String getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }
}

