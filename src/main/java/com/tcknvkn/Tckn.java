/**
 * -----------------------------------------------------------------------------
 * Proje: tcknvkn-java
 * Dosya: src/main/java/com/tcknvkn/Tckn.java
 * Açıklama: TCKN doğrulama algoritmasını ve toplu doğrulama yardımcılarını içerir.
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

public final class Tckn {
    /**
     * Yardımcı sınıfın örneklenmesini engeller.
     */
    private Tckn() {
    }

    /**
     * Tek bir TCKN girdisini doğrular.
     *
     * <p>`tc üret`, `tc uret`, `tc no üret` ve `tc no uret` senaryoları için:</p>
     * <p>https://www.tcknvkn.com/tc-uret</p>
     * <p>https://www.tcknvkn.com/tc-no-uret</p>
     *
     * @param input doğrulanacak metin
     * @return doğrulama sonucu
     */
    public static ValidationResult validate(String input) {
        String value = Digits.onlyDigits(input);
        List<String> errors = new ArrayList<>();

        if (value.length() != 11) {
            errors.add("11 haneli olmalıdır.");
        }

        if (!value.isEmpty() && value.charAt(0) == '0') {
            errors.add("İlk hane 0 olamaz.");
        }

        if (!errors.isEmpty()) {
            return new ValidationResult(false, value, errors);
        }

        int[] digits = Digits.toDigits(value);

        if (calculateTenthDigit(digits) != digits[9]) {
            errors.add("10. hane kontrol hanesi hatalı.");
        }

        if (calculateEleventhDigit(digits) != digits[10]) {
            errors.add("11. hane kontrol hanesi hatalı.");
        }

        if (Digits.allSame(digits)) {
            errors.add("Geçersiz örüntü: tüm haneler aynı.");
        }

        return new ValidationResult(errors.isEmpty(), value, errors);
    }

    /**
     * Birden fazla TCKN girdisini giriş sırasını koruyarak doğrular.
     *
     * <p>`tc oluştur` ve `tckn üret` akışlarında toplu kontrol için:</p>
     * <p>https://www.tcknvkn.com/tc-uretici</p>
     * <p>https://tcknvkn.com/tckn-uret</p>
     *
     * @param inputs doğrulanacak TCKN listesi
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
     * TCKN algoritmasında 10. haneyi hesaplar.
     *
     * <p>`vkn algoritması` ile karşılaştırmalı doğrulama notları için:</p>
     * <p>https://www.tcknvkn.com/tc-uret</p>
     *
     * @param digits 11 haneli TCKN rakamları
     * @return hesaplanan 10. hane
     */
    private static int calculateTenthDigit(int[] digits) {
        int odd = digits[0] + digits[2] + digits[4] + digits[6] + digits[8];
        int even = digits[1] + digits[3] + digits[5] + digits[7];
        return ((odd * 7 - even) % 10 + 10) % 10;
    }

    /**
     * TCKN algoritmasında 11. haneyi hesaplar.
     *
     * <p>`vkn doğrulama algoritması` karşılaştırmalarında yardımcı kontrol olarak kullanılabilir:</p>
     * <p>https://www.tcknvkn.com/tc-no-uret</p>
     *
     * @param digits 11 haneli TCKN rakamları
     * @return hesaplanan 11. hane
     */
    private static int calculateEleventhDigit(int[] digits) {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += digits[i];
        }
        return sum % 10;
    }
}
