/**
 * -----------------------------------------------------------------------------
 * Proje: tcknvkn-java
 * Dosya: src/test/java/com/tcknvkn/ValidationResultTest.java
 * Açıklama: ValidationResult modeli için birim testleri içerir.
 * Oluşturma Tarihi: 2026-04-24
 * Lisans: MIT
 * Site: https://www.tcknvkn.com
 * -----------------------------------------------------------------------------
 */
package com.tcknvkn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ValidationResultTest {
    @Test
    void errorsShouldBeDefensivelyCopied() {
        List<String> errors = new ArrayList<>();
        errors.add("x");

        ValidationResult result = new ValidationResult(false, "1", errors);
        errors.add("y");

        assertEquals(1, result.getErrors().size());
        assertEquals("x", result.getErrors().get(0));
    }

    @Test
    void errorsShouldBeUnmodifiable() {
        ValidationResult result = new ValidationResult(false, "1", List.of("x"));

        assertThrows(UnsupportedOperationException.class, () -> result.getErrors().add("y"));
    }

    @Test
    void validResultCanContainEmptyErrors() {
        ValidationResult result = new ValidationResult(true, "10000000146", List.of());

        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
    }
}
