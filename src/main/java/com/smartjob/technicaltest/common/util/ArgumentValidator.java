package com.smartjob.technicaltest.common.util;

import com.smartjob.technicaltest.common.exception.BusinessException;
import com.smartjob.technicaltest.common.exception.ErrorCodesEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentValidator {


    private ArgumentValidator() {
    }

    public static void requireNotNull(String value, String field) {
        if (value.isBlank()) {
            throw new BusinessException(ErrorCodesEnum.NOT_NULL, field);
        }
    }

    public static void validFormatEmail(String emailAddress, String field) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");

        if (!pattern.matcher(emailAddress).matches()) {
            throw new BusinessException(ErrorCodesEnum.IT_IS_NOT_AN_EMAIL, field);
        }
    }

    public static void onlyAcceptLetters(String value, String field) {
        Pattern pattern = Pattern.compile("^[\\p{L} \\p{M}]+$");

        if (!pattern.matcher(value).matches()) {
            throw new BusinessException(ErrorCodesEnum.NOT_VALUES_DIFFERENT_FROM_LETTERS, field);
        }
    }

    public static void requireLength(String value, int length, String message) {
        if (value.length() < length) {
            throw new BusinessException(ErrorCodesEnum.NOT_NULL);
        }
    }

    public static <T> void listNotEmpty(List<T> list, String field) {
        if (list.isEmpty()) {
            throw new BusinessException(ErrorCodesEnum.THE_LIST_CANNOT_BE_EMPTY, field);
        }
    }

    public static void requirePositiveAndGreaterThanZero(String value, String message) {
        if (Long.parseLong(value) <= 0) {
            throw new BusinessException(ErrorCodesEnum.THE_FIELD_CANNOT_BE_LESS_THAN_OR_EQUAL_TO_ZERO);
        }
    }

    public static void requireEqual(Double value, Double expectedValue, String message) {
        if (!value.equals(expectedValue)) {
            throw new BusinessException(ErrorCodesEnum.NOT_NULL);
        }
    }

    public static void requireMinimumLength(Object value, int minLength, String message) {
        if (value.toString().length() < minLength) {
            throw new BusinessException(ErrorCodesEnum.NOT_NULL);
        }
    }

    public static void requireLess(LocalDateTime initialDate, LocalDateTime finalDate, String message) {
        if (initialDate.toLocalDate().isAfter(finalDate.toLocalDate())) {
            throw new BusinessException(ErrorCodesEnum.NOT_NULL);
        }
    }

    public static void requireLess(Long initialNumber, Long finalNumber, String message) {
        if (initialNumber > finalNumber) {
            throw new BusinessException(ErrorCodesEnum.NOT_NULL);
        }
    }

    public static void requireNumeric(String value, String field) {
        try {
            Long.parseLong(value);
        } catch (NumberFormatException numberFormatException) {
            throw new BusinessException(ErrorCodesEnum.IT_IS_NOT_NUMERIC, field);
        }
    }

    public static void allowedStates(List<String> statusAvailable, String status, String message) {
        if (!statusAvailable.contains(status)) {
            throw new BusinessException(ErrorCodesEnum.NOT_NULL);
        }
    }

}
