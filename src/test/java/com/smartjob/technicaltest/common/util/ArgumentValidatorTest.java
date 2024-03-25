package com.smartjob.technicaltest.common.util;

import com.smartjob.technicaltest.common.exception.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArgumentValidatorTest {

    @Test
    public void testRequireNotEmpty() {
        // Arrange - Act - Assert
        assertThrows(BusinessException.class, () -> ArgumentValidator.requireNotEmpty(" ", "fieldName"));
    }

    @Test
    public void testValidFormatEmail() {
        // Arrange - Act - Assert
        assertThrows(BusinessException.class, () -> ArgumentValidator.validFormatEmail("invalidEmail", "email"));
    }

    @Test
    public void testOnlyAcceptLetters() {
        // Arrange - Act - Assert
        assertThrows(BusinessException.class, () -> ArgumentValidator.onlyAcceptLetters("123", "fieldName"));
    }

    @Test
    public void testListNotEmpty() {
        // Arrange - Act - Assert
        assertThrows(BusinessException.class, () -> ArgumentValidator.listNotEmpty(null, "list"));
    }

    @Test
    public void testRequirePositiveAndGreaterThanZero() {
        // Arrange - Act - Assert
        assertThrows(BusinessException.class, () -> ArgumentValidator.requirePositiveAndGreaterThanZero("0", "field"));
    }

    @Test
    public void testRequireNumeric() {
        // Arrange - Act - Assert
        assertThrows(BusinessException.class, () -> ArgumentValidator.requireNumeric("684516sda", "field"));
    }

    @Test
    public void testMustMeetTheConfiguredSpecifications() {
        // Arrange - Act - Assert
        assertThrows(BusinessException.class, () -> ArgumentValidator.mustMeetTheConfiguredSpecifications("\\d+", "notMatching", "field"));
    }
}