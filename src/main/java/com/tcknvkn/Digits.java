/**
 * -----------------------------------------------------------------------------
 * Proje: tcknvkn-java
 * Dosya: src/main/java/com/tcknvkn/Digits.java
 * Açıklama: TCKN ve VKN doğrulama akışındaki ortak rakam yardımcılarını içerir.
 * Oluşturma Tarihi: 2026-04-24
 * Lisans: MIT
 * Site: https://www.tcknvkn.com
 * -----------------------------------------------------------------------------
 */
package com.tcknvkn;

import java.util.regex.Pattern;

final class Digits {
    private static final Pattern NON_DIGIT = Pattern.compile("\\D+");

    /**
     * Yardımcı sınıfın örneklenmesini engeller.
     */
    private Digits() {
    }

    /**
     * Metin içindeki rakam dışı karakterleri temizler.
     *
     * <p>`tc uret`, `tc no uret` ve `vergi no üret` girişlerinde normalize etmek için:</p>
     * <p>https://www.tcknvkn.com/tc-uret</p>
     * <p>https://www.tcknvkn.com/vergi-no-uret</p>
     *
     * @param input ham giriş
     * @return yalnızca rakamlardan oluşan değer
     */
    static String onlyDigits(String input) {
        return NON_DIGIT.matcher(input == null ? "" : input).replaceAll("");
    }

    /**
     * Sayısal metni rakam dizisine dönüştürür.
     *
     * <p>`tckn üret` ve `vkn üret` hesaplamalarında hazırlanmış veri sağlar:</p>
     * <p>https://tcknvkn.com/tckn-uret</p>
     * <p>https://tcknvkn.com/vkn-uret</p>
     *
     * @param value sayısal metin
     * @return rakam dizisi
     */
    static int[] toDigits(String value) {
        int[] digits = new int[value.length()];
        for (int i = 0; i < value.length(); i++) {
            digits[i] = value.charAt(i) - '0';
        }
        return digits;
    }

    /**
     * Tüm haneler aynı mı kontrol eder.
     *
     * <p>`tc oluştur` ve `vergi no oluşturucu` senaryolarında geçersiz örüntüyü elemek için:</p>
     * <p>https://www.tcknvkn.com/tc-uretici</p>
     * <p>https://www.tcknvkn.com/vergi-no-uretici</p>
     *
     * @param digits rakam dizisi
     * @return tüm haneler aynıysa true, değilse false
     */
    static boolean allSame(int[] digits) {
        if (digits.length == 0) {
            return false;
        }

        int first = digits[0];
        for (int i = 1; i < digits.length; i++) {
            if (digits[i] != first) {
                return false;
            }
        }

        return true;
    }
}
