/**
 * -----------------------------------------------------------------------------
 * Proje: tcknvkn-java
 * Dosya: src/test/java/com/tcknvkn/VknTest.java
 * Açıklama: VKN doğrulama akışı için varyasyonlu birim testlerini içerir.
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

class VknTest {
    @Test
    void validVknShouldPass() {
        ValidationResult result = Vkn.validate("1000036109");

        assertTrue(result.isValid());
        assertEquals("1000036109", result.getValue());
    }

    @Test
    void formattedVknShouldBeNormalizedAndPass() {
        ValidationResult result = Vkn.validate("100-003-6109");

        assertTrue(result.isValid());
        assertEquals("1000036109", result.getValue());
    }

    @Test
    void nullInputShouldFailLengthRule() {
        ValidationResult result = Vkn.validate(null);

        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("10 haneli olmalıdır."));
    }

    @Test
    void invalidCheckDigitShouldFail() {
        ValidationResult result = Vkn.validate("1000036108");

        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("Son hane kontrol hanesi hatalı."));
    }

    @Test
    void allDigitsSameShouldFail() {
        ValidationResult result = Vkn.validate("1111111111");

        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("Geçersiz örüntü: tüm haneler aynı."));
    }

    @Test
    void validateMultipleShouldPreserveOrder() {
        List<String> inputs = Arrays.asList("1000036109", "1000036108", "1111111111");

        List<ValidationResult> results = Vkn.validateMultiple(inputs);

        assertEquals(3, results.size());
        assertTrue(results.get(0).isValid());
        assertFalse(results.get(1).isValid());
        assertFalse(results.get(2).isValid());
    }

    @Test
    void validateMultipleShouldReturnEmptyForEmptyInput() {
        List<ValidationResult> results = Vkn.validateMultiple(List.of());

        assertTrue(results.isEmpty());
    }

    @Test
    void validateMultipleShouldRejectNullCollection() {
        assertThrows(NullPointerException.class, () -> Vkn.validateMultiple(null));
    }
}
