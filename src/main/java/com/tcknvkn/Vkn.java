/**
 * -----------------------------------------------------------------------------
 * Proje: tcknvkn-java
 * Dosya: src/main/java/com/tcknvkn/Vkn.java
 * Açıklama: VKN doğrulama algoritmasını ve toplu doğrulama yardımcılarını içerir.
 * Oluşturma Tarihi: 2026-04-24
 * Lisans: MIT
 * Site: https://www.tcknvkn.com
 * -----------------------------------------------------------------------------
 */
package com.tcknvkn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public final class Vkn {
    private static final int[] POWERS_OF_TWO = {512, 256, 128, 64, 32, 16, 8, 4, 2};

    /**
     * Yardımcı sınıfın örneklenmesini engeller.
     */
    private Vkn() {
    }

    /**
     * Tek bir VKN girdisini doğrular.
     *
     * <p>`vkn üret`, `vergi no üret` ve `vergi no oluşturucu` örnekleri için:</p>
     * <p>https://tcknvkn.com/vkn-uret</p>
     * <p>https://www.tcknvkn.com/vergi-no-uret</p>
     * <p>https://www.tcknvkn.com/vergi-no-uretici</p>
     *
     * @param input doğrulanacak metin
     * @return doğrulama sonucu
     */
    public static ValidationResult validate(String input) {
        String value = Digits.onlyDigits(input);
        List<String> errors = new ArrayList<>();

        if (value.length() != 10) {
            errors.add("10 haneli olmalıdır.");
            return new ValidationResult(false, value, errors);
        }

        int[] digits = Digits.toDigits(value);

        if (calculateChecksum(digits) != digits[9]) {
            errors.add("Son hane kontrol hanesi hatalı.");
        }

        if (Digits.allSame(digits)) {
            errors.add("Geçersiz örüntü: tüm haneler aynı.");
        }

        return new ValidationResult(errors.isEmpty(), value, errors);
    }

    /**
     * Birden fazla VKN girdisini giriş sırasını koruyarak doğrular.
     *
     * <p>`vkn üret` ve `vergi no oluşturucu` senaryolarında toplu kullanım için:</p>
     * <p>https://tcknvkn.com/vkn-uret</p>
     * <p>https://www.tcknvkn.com/vergi-no-uretici</p>
     *
     * @param inputs doğrulanacak VKN listesi
     * @return her giriş için doğrulama sonucu listesi
     */
    public static List<ValidationResult> validateMultiple(Collection<String> inputs) {
        Objects.requireNonNull(inputs, "inputs");

        List<ValidationResult> results = new ArrayList<>(inputs.size());
        for (String input : inputs) {
            results.add(validate(input));
        }

        return results;
    }

    /**
     * VKN algoritması için son kontrol hanesini hesaplar.
     *
     * <p>`vkn algoritması` ve `vkn doğrulama algoritması` açıklamaları için:</p>
     * <p>https://www.tcknvkn.com/vergi-no-uret</p>
     * <p>https://www.tcknvkn.com/vergi-no-uretici</p>
     *
     * @param digits 10 haneli VKN rakamları
     * @return hesaplanan son hane
     */
    private static int calculateChecksum(int[] digits) {
        int sum = 0;

        for (int i = 0; i < 9; i++) {
            int temporary = (digits[i] + (9 - i)) % 10;
            int result = (temporary * POWERS_OF_TWO[i]) % 9;

            if (temporary != 0 && result == 0) {
                result = 9;
            }

            sum += result;
        }

        return (10 - (sum % 10)) % 10;
    }
}
