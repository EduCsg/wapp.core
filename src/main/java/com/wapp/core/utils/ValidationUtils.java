package com.wapp.core.utils;

import java.util.List;

public class ValidationUtils {

	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty() || str.isBlank();
	}

	public static boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty() || list.get(0) == null;
	}

	public static boolean isEmpty(Object obj) {
		return obj == null || obj.toString().isEmpty();
	}

	public static boolean notEmpty(String value) {
		return value != null && !value.isEmpty() && !value.isBlank();
	}

	public static boolean notEmpty(List<?> list) {
		return list != null && !list.isEmpty() && list.get(0) != null;
	}

}
