package src.gui;

import javax.swing.*;
import java.awt.*;
import src.services.BankService;
import src.models.Customer;
import src.models.Account;
import java.util.List;
import javax.swing.border.EmptyBorder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainFrame extends JFrame {
    private BankService bankService;
    private Customer currentCustomer;
    private JPanel mainPanel;
    private JPanel contentPanel;
    private Color primaryColor = new Color(0, 28, 85); // Yapı Kredi Lacivert
    private Color accentColor = new Color(231, 27, 74); // Yapı Kredi Kırmızı
    private JLabel balanceLabel;
    
    public MainFrame(BankService bankService, Customer customer) {
        this.bankService = bankService;
        this.currentCustomer = customer;
        setupUI();
    }
    
    private void setupUI() {
        setTitle("Dijital Bankacılık - " + currentCustomer.getName());
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(Color.WHITE);
        
        // Üst banner
        JPanel topBanner = createTopBanner();
        mainPanel.add(topBanner, BorderLayout.NORTH);
        
        // Sol menü
        JPanel sidePanel = createSidePanel();
        mainPanel.add(sidePanel, BorderLayout.WEST);
        
        // Ana içerik paneli
        contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(new Color(245, 245, 250));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        showDashboard(); // Varsayılan görünüm
        
        add(mainPanel);
    }
    
    private JPanel createTopBanner() {
        JPanel banner = new JPanel(new BorderLayout());
        banner.setBackground(primaryColor);
        banner.setPreferredSize(new Dimension(getWidth(), 60));
        banner.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        // Sol taraf - Logo ve müşteri bilgisi
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);
        
        JLabel bankLabel = new JLabel("DİJİTAL BANKA");
        bankLabel.setFont(new Font("Arial", Font.BOLD, 20));
        bankLabel.setForeground(Color.WHITE);
        
        leftPanel.add(bankLabel);
        
        // Sağ taraf - Tarih ve çıkış
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        JLabel dateLabel = new JLabel(sdf.format(new Date()));
        dateLabel.setForeground(Color.WHITE);
        
        JButton logoutBtn = new JButton("Çıkış");
        styleButton(logoutBtn);
        logoutBtn.addActionListener(e -> handleLogout());
        
        rightPanel.add(dateLabel);
        rightPanel.add(Box.createHorizontalStrut(20));
        rightPanel.add(logoutBtn);
        
        banner.add(leftPanel, BorderLayout.WEST);
        banner.add(rightPanel, BorderLayout.EAST);
        
        return banner;
    }
    
    private void showDashboard() {
        contentPanel.removeAll();
        
        // Üst panel - Toplam bakiye ve hızlı işlemler
        JPanel topPanel = new JPanel(new BorderLayout(20, 0));
        topPanel.setOpaque(false);
        
        // Bakiye kartı
        JPanel balanceCard = createBalanceCard();
        topPanel.add(balanceCard, BorderLayout.WEST);
        
        // Hızlı işlemler
        JPanel quickActionsPanel = createQuickActionsPanel();
        topPanel.add(quickActionsPanel, BorderLayout.CENTER);
        
        // Hesaplar listesi
        JPanel accountsPanel = createAccountsPanel();
        
        // Son işlemler
        JPanel transactionsPanel = createTransactionsPanel();
        
        // Layout
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        centerPanel.setOpaque(false);
        centerPanel.add(accountsPanel);
        centerPanel.add(transactionsPanel);
        
        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Döviz kurları paneli
        JPanel exchangeRatesPanel = createExchangeRatesPanel();
        JPanel rightPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        rightPanel.setOpaque(false);
        rightPanel.add(exchangeRatesPanel);
        rightPanel.add(createPromotionsPanel());

        contentPanel.add(rightPanel, BorderLayout.EAST);
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private JPanel createBalanceCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(primaryColor);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        card.setPreferredSize(new Dimension(300, 150));
        
        JLabel titleLabel = new JLabel("Toplam Varlık");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
        double totalBalance = currentCustomer.getAccounts().stream()
                            .mapToDouble(Account::getBalance)
                            .sum();
        
        balanceLabel = new JLabel(String.format("%.2f TL", totalBalance));
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(balanceLabel);
        
        return card;
    }
    
    private JPanel createQuickActionsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panel.setOpaque(false);
        
        String[] actions = {"Para Transferi", "Yeni Hesap", "Hesap Özeti", "Ayarlar"};
        for (String action : actions) {
            JButton button = createQuickActionButton(action);
            panel.add(button);
        }
        
        return panel;
    }
    
    private JButton createQuickActionButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 40));
        button.setBackground(Color.WHITE);
        button.setForeground(primaryColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(primaryColor));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        
        button.addActionListener(e -> handleQuickAction(text));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(primaryColor);
                button.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
                button.setForeground(primaryColor);
            }
        });
        
        return button;
    }
    
    private JPanel createAccountsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Hesaplarım");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel accountsGrid = new JPanel(new GridLayout(0, 2, 10, 10));
        accountsGrid.setOpaque(false);
        
        for (Account account : currentCustomer.getAccounts()) {
            accountsGrid.add(createAccountCard(account));
        }
        
        JScrollPane scrollPane = new JScrollPane(accountsGrid);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createAccountCard(Account account) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel accountTypeLabel = new JLabel(account instanceof src.models.SavingsAccount ? "Tasarruf Hesabı" : "Vadesiz Hesap");
        accountTypeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel accountNumberLabel = new JLabel(account.getAccountNumber());
        accountNumberLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        accountNumberLabel.setForeground(Color.GRAY);
        
        JLabel balanceLabel = new JLabel(String.format("%.2f TL", account.getBalance()));
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        if (account == currentCustomer.getMainAccount()) {
            JLabel mainAccountLabel = new JLabel("Ana Hesap");
            mainAccountLabel.setForeground(accentColor);
            mainAccountLabel.setFont(new Font("Arial", Font.BOLD, 12));
            card.add(mainAccountLabel);
            card.add(Box.createVerticalStrut(5));
        }
        
        card.add(accountTypeLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(accountNumberLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(balanceLabel);
        
        return card;
    }
    
    private JPanel createTransactionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Son İşlemler");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Burada son işlemler listesi gösterilecek
        JLabel placeholderLabel = new JLabel("İşlem geçmişi henüz oluşturulmadı.");
        placeholderLabel.setHorizontalAlignment(JLabel.CENTER);
        placeholderLabel.setForeground(Color.GRAY);
        panel.add(placeholderLabel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void handleQuickAction(String action) {
        switch (action) {
            case "Para Transferi":
                showTransferDialog();
                break;
            case "Yeni Hesap":
                showNewAccountDialog();
                break;
            case "Hesap Özeti":
                showAccountSummary();
                break;
            case "Ayarlar":
                showSettings();
                break;
        }
    }
    
    private void showAccountSummary() {
        // Hesap özeti dialogu
        JDialog dialog = new JDialog(this, "Hesap Özeti", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Hesap bilgileri
        StringBuilder summary = new StringBuilder();
        summary.append("MÜŞTERİ BİLGİLERİ\n");
        summary.append("----------------------------------------\n");
        summary.append("Müşteri ID: ").append(currentCustomer.getId()).append("\n");
        summary.append("Ad: ").append(currentCustomer.getName()).append("\n\n");
        
        summary.append("HESAP BİLGİLERİ\n");
        summary.append("----------------------------------------\n");
        for (Account account : currentCustomer.getAccounts()) {
            summary.append("\nHesap No: ").append(account.getAccountNumber()).append("\n");
            summary.append("Tür: ").append(account instanceof src.models.SavingsAccount ? "Tasarruf Hesabı" : "Vadesiz Hesap").append("\n");
            summary.append("Bakiye: ").append(String.format("%.2f TL", account.getBalance())).append("\n");
            if (account == currentCustomer.getMainAccount()) {
                summary.append("(Ana Hesap)\n");
            }
        }
        
        JTextArea summaryArea = new JTextArea(summary.toString());
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        JScrollPane scrollPane = new JScrollPane(summaryArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JButton closeButton = new JButton("Kapat");
        styleButton(closeButton);
        closeButton.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void showSettings() {
        JOptionPane.showMessageDialog(this,
            "Ayarlar özelliği yakında eklenecektir.",
            "Bilgi",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void styleButton(JButton button) {
        button.setBackground(accentColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(200, 23, 63));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(accentColor);
            }
        });
    }
    
    private JPanel createSidePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(30, 35, 40));
        panel.setPreferredSize(new Dimension(250, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Müşteri bilgileri
        JLabel customerLabel = new JLabel("Hoş Geldiniz,");
        customerLabel.setForeground(new Color(180, 180, 180));
        customerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        customerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel nameLabel = new JLabel(currentCustomer.getName());
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel idLabel = new JLabel("ID: " + currentCustomer.getId());
        idLabel.setForeground(new Color(180, 180, 180));
        idLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        idLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Menü öğeleri
        String[] menuItems = {
            "Ana Sayfa",
            "Hesaplarım",
            "Para Transferleri",
            "Ödemeler",
            "Kartlarım",
            "Ayarlar"
        };

        panel.add(customerLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(idLabel);
        panel.add(Box.createVerticalStrut(40));

        // Menü butonları
        for (String menuItem : menuItems) {
            JButton menuButton = createSideMenuButton(menuItem);
            panel.add(menuButton);
            panel.add(Box.createVerticalStrut(10));
        }

        return panel;
    }

    private JButton createSideMenuButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(250, 40));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setBackground(new Color(30, 35, 40));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("Arial", Font.PLAIN, 14));

        // Hover efekti
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(45, 50, 55));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(30, 35, 40));
            }
        });

        // Menü öğelerine tıklama işlemleri
        button.addActionListener(e -> handleSideMenuAction(text));

        return button;
    }

    private void handleSideMenuAction(String menuItem) {
        switch (menuItem) {
            case "Ana Sayfa":
                showDashboard();
                break;
            case "Hesaplarım":
                showAccountSummary();
                break;
            case "Para Transferleri":
                showTransferDialog();
                break;
            case "Ödemeler":
                JOptionPane.showMessageDialog(this,
                    "Ödeme işlemleri yakında eklenecektir.",
                    "Bilgi",
                    JOptionPane.INFORMATION_MESSAGE);
                break;
            case "Kartlarım":
                JOptionPane.showMessageDialog(this,
                    "Kart işlemleri yakında eklenecektir.",
                    "Bilgi",
                    JOptionPane.INFORMATION_MESSAGE);
                break;
            case "Ayarlar":
                showSettings();
                break;
        }
    }
    
    private void handleLogout() {
        int choice = JOptionPane.showConfirmDialog(this,
            "Çıkış yapmak istediğinizden emin misiniz?",
            "Çıkış",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (choice == JOptionPane.YES_OPTION) {
            // Çıkış işlemleri
            LoginFrame loginFrame = new LoginFrame(bankService);
            loginFrame.setVisible(true);
            this.dispose(); // Mevcut pencereyi kapat
        }
    }
    
    private void showTransferDialog() {
        JDialog dialog = new JDialog(this, "Para Transferi", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Transfer türü seçimi
        String[] options = {"Hesap Numarasına", "Müşteri ID'sine"};
        JComboBox<String> transferType = new JComboBox<>(options);
        
        // Gönderen hesap seçimi
        JComboBox<String> fromAccount = new JComboBox<>();
        for (Account acc : currentCustomer.getAccounts()) {
            fromAccount.addItem(acc.getAccountNumber() + " - " + 
                String.format("%.2f TL", acc.getBalance()));
        }
        
        // Alıcı bilgisi
        JTextField toField = new JTextField(20);
        
        // Miktar
        JTextField amountField = new JTextField(20);
        
        // Butonlar
        JButton sendButton = new JButton("Gönder");
        JButton cancelButton = new JButton("İptal");
        
        styleButton(sendButton);
        styleButton(cancelButton);
        
        // Panel düzeni
        panel.add(new JLabel("Transfer Türü:"));
        panel.add(Box.createVerticalStrut(5));
        panel.add(transferType);
        panel.add(Box.createVerticalStrut(15));
        
        panel.add(new JLabel("Gönderen Hesap:"));
        panel.add(Box.createVerticalStrut(5));
        panel.add(fromAccount);
        panel.add(Box.createVerticalStrut(15));
        
        panel.add(new JLabel("Alıcı Hesap No/Müşteri ID:"));
        panel.add(Box.createVerticalStrut(5));
        panel.add(toField);
        panel.add(Box.createVerticalStrut(15));
        
        panel.add(new JLabel("Miktar (TL):"));
        panel.add(Box.createVerticalStrut(5));
        panel.add(amountField);
        panel.add(Box.createVerticalStrut(20));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(sendButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel);
        
        // Buton işlevleri
        sendButton.addActionListener(e -> {
            try {
                String fromAccountNumber = fromAccount.getSelectedItem().toString().split(" - ")[0];
                String to = toField.getText();
                double amount = Double.parseDouble(amountField.getText());
                
                boolean success;
                if (transferType.getSelectedIndex() == 0) {
                    success = bankService.transferByAccountNumbers(fromAccountNumber, to, amount);
                } else {
                    success = bankService.transferToCustomer(fromAccountNumber, to, amount);
                }
                
                if (success) {
                    JOptionPane.showMessageDialog(dialog,
                        "Transfer başarıyla gerçekleştirildi.",
                        "Başarılı",
                        JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    updateAccountsDisplay();
                } else {
                    JOptionPane.showMessageDialog(dialog,
                        "Transfer başarısız oldu. Lütfen bilgileri kontrol edin.",
                        "Hata",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog,
                    "Lütfen geçerli bir miktar girin.",
                    "Hata",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showNewAccountDialog() {
        String[] options = {"Vadesiz Hesap", "Tasarruf Hesabı"};
        int choice = JOptionPane.showOptionDialog(this,
            "Açmak istediğiniz hesap türünü seçin:",
            "Yeni Hesap",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        
        if (choice != JOptionPane.CLOSED_OPTION) {
            bankService.createAccount(currentCustomer, choice == 1 ? "Savings" : "Checking");
            updateAccountsDisplay();
            JOptionPane.showMessageDialog(this,
                "Yeni hesap başarıyla oluşturuldu.",
                "Başarılı",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateAccountsDisplay() {
        // Toplam bakiye güncelleme
        double totalBalance = currentCustomer.getAccounts().stream()
                            .mapToDouble(Account::getBalance)
                            .sum();
        balanceLabel.setText(String.format("%.2f TL", totalBalance));
        
        // Hesaplar panelini güncelle
        contentPanel.removeAll();
        
        // Üst panel - Toplam bakiye ve hızlı işlemler
        JPanel topPanel = new JPanel(new BorderLayout(20, 0));
        topPanel.setOpaque(false);
        
        // Bakiye kartı
        JPanel balanceCard = createBalanceCard();
        topPanel.add(balanceCard, BorderLayout.WEST);
        
        // Hızlı işlemler
        JPanel quickActionsPanel = createQuickActionsPanel();
        topPanel.add(quickActionsPanel, BorderLayout.CENTER);
        
        // Hesaplar listesi
        JPanel accountsPanel = createAccountsPanel();
        
        // Son işlemler
        JPanel transactionsPanel = createTransactionsPanel();
        
        // Layout
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        centerPanel.setOpaque(false);
        centerPanel.add(accountsPanel);
        centerPanel.add(transactionsPanel);
        
        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Paneli yenile
        contentPanel.revalidate();
        contentPanel.repaint();
        
        // Ana pencereyi yenile
        this.revalidate();
        this.repaint();
    }

    private JPanel createExchangeRatesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setPreferredSize(new Dimension(300, 0));

        JLabel titleLabel = new JLabel("Döviz Kurları");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel ratesPanel = new JPanel(new GridLayout(0, 1, 0, 10));
        ratesPanel.setOpaque(false);

        // Örnek döviz kurları
        String[][] rates = {
            {"USD/TL", "28.50", "28.70"},
            {"EUR/TL", "30.80", "31.00"},
            {"GBP/TL", "35.90", "36.10"}
        };

        for (String[] rate : rates) {
            JPanel ratePanel = new JPanel(new BorderLayout(10, 0));
            ratePanel.setOpaque(false);
            ratePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));

            JLabel currencyLabel = new JLabel(rate[0]);
            currencyLabel.setFont(new Font("Arial", Font.BOLD, 14));

            JPanel valuesPanel = new JPanel(new GridLayout(1, 2, 10, 0));
            valuesPanel.setOpaque(false);

            JLabel buyLabel = new JLabel("Alış: " + rate[1]);
            JLabel sellLabel = new JLabel("Satış: " + rate[2]);

            valuesPanel.add(buyLabel);
            valuesPanel.add(sellLabel);

            ratePanel.add(currencyLabel, BorderLayout.WEST);
            ratePanel.add(valuesPanel, BorderLayout.EAST);

            ratesPanel.add(ratePanel);
        }

        panel.add(ratesPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createPromotionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Kampanyalar");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel promotionsPanel = new JPanel(new GridLayout(0, 1, 0, 10));
        promotionsPanel.setOpaque(false);

        String[] promotions = {
            "Kredi Kartı - %10 Geri Ödeme",
            "İhtiyaç Kredisi - %1,99'dan başlayan faiz",
            "Vadeli Mevduat - %30 Faiz Fırsatı"
        };

        for (String promo : promotions) {
            JPanel promoPanel = createPromotionCard(promo);
            promotionsPanel.add(promoPanel);
        }

        panel.add(promotionsPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createPromotionCard(String promoText) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel promoLabel = new JLabel(promoText);
        promoLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton detailsButton = new JButton("Detaylar");
        styleButton(detailsButton);
        detailsButton.addActionListener(e -> 
            JOptionPane.showMessageDialog(this,
                "Bu kampanya detayları yakında eklenecektir.",
                "Kampanya Detayları",
                JOptionPane.INFORMATION_MESSAGE)
        );

        panel.add(promoLabel, BorderLayout.CENTER);
        panel.add(detailsButton, BorderLayout.EAST);

        return panel;
    }
} 