package com.wapp.core.utils;

public class MethodsUtils {

    public static boolean Empty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean notEmpty(String value) {
        return value != null && ! value.isEmpty();
    }

}
