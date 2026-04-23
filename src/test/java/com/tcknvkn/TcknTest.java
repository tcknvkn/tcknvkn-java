/**
 * -----------------------------------------------------------------------------
 * Proje: tcknvkn-java
 * Dosya: src/test/java/com/tcknvkn/TcknTest.java
 * Açıklama: TCKN doğrulama akışı için varyasyonlu birim testlerini içerir.
 * Oluşturma Tarihi: 2026-04-24
 * Lisans: MIT
 * Site: https://www.tcknvkn.com
 * -----------------------------------------------------------------------------
 */
package com.tcknvkn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class TcknTest {
    @Test
    void validTcknShouldPass() {
        ValidationResult result = Tckn.validate("10000000146");

        assertTrue(result.isValid());
        assertEquals("10000000146", result.getValue());
        assertTrue(result.getErrors().isEmpty());
    }

    @Test
    void formattedTcknShouldBeNormalizedAndPass() {
        ValidationResult result = Tckn.validate("100-000-001 46");

        assertTrue(result.isValid());
        assertEquals("10000000146", result.getValue());
    }

    @Test
    void nullInputShouldFailLengthRule() {
        ValidationResult result = Tckn.validate(null);

        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("11 haneli olmalıdır."));
    }

    @Test
    void firstDigitZeroShouldFail() {
        ValidationResult result = Tckn.validate("01234567890");

        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("İlk hane 0 olamaz."));
    }

    @Test
    void checkDigitMismatchShouldFail() {
        ValidationResult result = Tckn.validate("10000000145");

        assertFalse(result.isValid());
        assertTrue(result.getErrors().stream().anyMatch(x -> x.contains("kontrol hanesi")));
    }

    @Test
    void allDigitsSameShouldFail() {
        ValidationResult result = Tckn.validate("11111111111");

        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("Geçersiz örüntü: tüm haneler aynı."));
    }

    @Test
    void shortAndLeadingZeroShouldCollectMultipleErrors() {
        ValidationResult result = Tckn.validate("0");

        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("11 haneli olmalıdır."));
        assertTrue(result.getErrors().contains("İlk hane 0 olamaz."));
    }

    @Test
    void validateMultipleShouldPreserveOrder() {
        List<String> inputs = Arrays.asList("10000000146", "10000000145", "11111111111");

        List<ValidationResult> results = Tckn.validateMultiple(inputs);

        assertEquals(3, results.size());
        assertTrue(results.get(0).isValid());
        assertFalse(results.get(1).isValid());
        assertFalse(results.get(2).isValid());
    }

    @Test
    void validateMultipleShouldReturnEmptyForEmptyInput() {
        List<ValidationResult> results = Tckn.validateMultiple(List.of());

        assertTrue(results.isEmpty());
    }

    @Test
    void validateMultipleShouldRejectNullCollection() {
        assertThrows(NullPointerException.class, () -> Tckn.validateMultiple(null));
    }
}
