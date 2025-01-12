# 🏦 Modern Java Bankacılık Uygulaması

<div align="center">
  <br>
  <p>Java Swing ile geliştirilmiş modern ve güvenli bankacılık uygulaması</p>
</div>

## 📌 Proje Hakkında

Bu proje, Java programlama dilini ve Swing GUI framework'ünü kullanarak geliştirilmiş kapsamlı bir bankacılık uygulamasıdır. OOP prensiplerini (Inheritance, Polymorphism, Encapsulation, Abstraction) temel alarak tasarlanmıştır.

## ✨ Özellikler

### 🔐 Güvenlik
- TC Kimlik doğrulama sistemi
- Şifreli giriş sistemi
- Güvenli oturum yönetimi

### 💳 Hesap İşlemleri
- Vadesiz ve tasarruf hesabı yönetimi
- Çoklu hesap desteği
- Ana hesap belirleme
- Otomatik hesap oluşturma

### 💸 Para İşlemleri
- Hesaplar arası transfer
- TC Kimlik ile para transferi
- Anlık bakiye görüntüleme
- Detaylı hesap özeti

### 📊 Finans Takibi
- Güncel döviz kurları
- Hesap hareketleri
- Kampanya takibi
- Finansal özet

## 🛠️ Teknik Özellikler

### Kullanılan Teknolojiler
- Java 8+
- Swing GUI Framework
- Custom Components
- MVC Mimari

### Tasarım Prensipleri
- SOLID Prensipleri
- Clean Code
- OOP Prensipleri
- Design Patterns

## ⚙️ Kurulum

1. Projeyi klonlayın: bash
git clone https://github.com/ae52/java-swing-banking-desktopapp.git
bash
cd banking-app
bash
javac -d bin src/.java src//.java
bash
java -cp bin src.Main
src/
├── Main.java # Ana uygulama başlangıç noktası
├── models/ # Veri modelleri
│ ├── Account.java # Temel hesap sınıfı
│ ├── Customer.java # Müşteri sınıfı
│ ├── SavingsAccount.java # Tasarruf hesabı
│ └── CheckingAccount.java # Vadesiz hesap
├── services/ # İş mantığı katmanı
│ └── BankService.java # Bankacılık işlemleri
├── interfaces/ # Arayüzler
│ └── BankingOperations.java
└── gui/ # Kullanıcı arayüzü
├── LoginFrame.java # Giriş ekranı
└── MainFrame.java # Ana uygulama ekranı

## 🤝 Katkıda Bulunma

1. Bu projeyi fork edin
2. Yeni bir branch oluşturun (`git checkout -b feature/yeniOzellik`)
3. Değişikliklerinizi commit edin (`git commit -am 'Yeni özellik eklendi'`)
4. Branch'inizi push edin (`git push origin feature/yeniOzellik`)
5. Pull Request oluşturun

## 📝 Lisans

Bu proje MIT lisansı altında lisanslanmıştır. Daha fazla bilgi için `LICENSE` dosyasına bakın.

## 👥 Yazarlar

- [AE52] - *İlk Geliştirici* - [AE52]

## 🙏 Teşekkürler

- Java Swing için Oracle