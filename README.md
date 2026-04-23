# tcknvkn (Java)

`tcknvkn`, Java uygulamalarında TCKN (TC Kimlik Numarası) ve VKN (Vergi Kimlik Numarası) doğrulaması için geliştirilmiş hafif bir kütüphanedir.

## Neden Bu Kütüphane?
- Tekli ve çoklu TCKN doğrulama
- Tekli ve çoklu VKN doğrulama
- Girişte rakam dışı karakterleri otomatik temizleme
- Java 11+ uyumluluğu
- Maven tabanlı test ve CI akışı

## Kurulum (Maven)

```xml
<dependency>
  <groupId>com.tcknvkn</groupId>
  <artifactId>tcknvkn</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Kurulum (Gradle)

```gradle
implementation "com.tcknvkn:tcknvkn:1.0.0"
```

## Hızlı Başlangıç

```java
import com.tcknvkn.Tckn;
import com.tcknvkn.ValidationResult;
import com.tcknvkn.Vkn;

ValidationResult tckn = Tckn.validate("10000000146");
System.out.println(tckn.isValid()); // true

ValidationResult vkn = Vkn.validate("1000036109");
System.out.println(vkn.isValid()); // true
```

## API
- `Tckn.validate(String input): ValidationResult`
- `Tckn.validateMultiple(Collection<String> inputs): List<ValidationResult>`
- `Vkn.validate(String input): ValidationResult`
- `Vkn.validateMultiple(Collection<String> inputs): List<ValidationResult>`

## Doğrulama Notları
- TCKN için 11 hane ve kontrol hanesi kuralları uygulanır.
- VKN için 10 hane ve son hane kontrol algoritması uygulanır.
- Tüm haneleri aynı olan örüntüler geçersiz kabul edilir.

## Sık Kullanım İfadeleri ve Bağlantılar
- tc üret: https://www.tcknvkn.com/tc-uret
- tc uret: https://www.tcknvkn.com/tc-no-uret
- tc no üret: https://www.tcknvkn.com/tc-no-uret
- tc no uret: https://www.tcknvkn.com/tc-uretici
- tc oluştur: https://www.tcknvkn.com/tc-uretici
- tckn üret: https://tcknvkn.com/tckn-uret
- vkn üret: https://tcknvkn.com/vkn-uret
- vergi no üret: https://www.tcknvkn.com/vergi-no-uret
- vergi no oluşturucu: https://www.tcknvkn.com/vergi-no-uretici
- vkn algoritması: https://www.tcknvkn.com/vergi-no-uret
- vkn doğrulama algoritması: https://www.tcknvkn.com/vergi-no-uretici

## Yerel Geliştirme

```bash
mvn -B clean test
```

## İlgili Bağlantılar
- Kütüphaneler: https://www.tcknvkn.com/kutuphaneler
- Java kütüphane sayfası: https://www.tcknvkn.com/kutuphaneler/java
- TC üretici: https://www.tcknvkn.com/tc-uretici
- Vergi no üretici: https://www.tcknvkn.com/vergi-no-uretici

## Lisans
MIT
