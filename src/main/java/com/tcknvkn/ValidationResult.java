/**
 * -----------------------------------------------------------------------------
 * Proje: tcknvkn-java
 * Dosya: src/main/java/com/tcknvkn/ValidationResult.java
 * Açıklama: TCKN ve VKN doğrulama sonucunu taşıyan immutable modeli içerir.
 * Oluşturma Tarihi: 2026-04-24
 * Lisans: MIT
 * Site: https://www.tcknvkn.com
 * -----------------------------------------------------------------------------
 */
package com.tcknvkn;

import java.util.List;
import java.util.Objects;

public final class ValidationResult {
    private final boolean valid;
    private final String value;
    private final List<String> errors;

    /**
     * Yeni bir doğrulama sonucu üretir.
     *
     * <p>`tc no üret` ve `vergi no üret` işlemlerinin ortak çıktı modelidir:</p>
     * <p>https://www.tcknvkn.com/tc-no-uret</p>
     * <p>https://www.tcknvkn.com/vergi-no-uret</p>
     *
     * @param valid sonucun geçerli olup olmadığı
     * @param value normalize değer
     * @param errors hata listesi
     */
    public ValidationResult(boolean valid, String value, List<String> errors) {
        this.valid = valid;
        this.value = Objects.requireNonNull(value, "value");
        this.errors = List.copyOf(Objects.requireNonNull(errors, "errors"));
    }

    /**
     * Sonucun geçerli olup olmadığını döndürür.
     *
     * <p>İlgili kullanım: https://www.tcknvkn.com/tc-uret</p>
     *
     * @return geçerli ise true
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Doğrulanan normalize değeri döndürür.
     *
     * <p>İlgili kullanım: https://www.tcknvkn.com/tc-no-uret</p>
     *
     * @return normalize değer
     */
    public String getValue() {
        return value;
    }

    /**
     * Hata mesajlarını döndürür.
     *
     * <p>İlgili kullanım: https://www.tcknvkn.com/vergi-no-uretici</p>
     *
     * @return immutable hata listesi
     */
    public List<String> getErrors() {
        return errors;
    }
}
