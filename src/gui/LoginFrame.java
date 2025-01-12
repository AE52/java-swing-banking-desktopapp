package src.gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import src.services.BankService;
import src.models.Customer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    private BankService bankService;
    private JPanel mainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    public LoginFrame(BankService bankService) {
        this.bankService = bankService;
        setupUI();
    }
    
    private void setupUI() {
        setTitle("Banka Uygulaması - Giriş");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Ekranın ortasında göster
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Logo veya başlık
        JLabel titleLabel = new JLabel("BANKA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Giriş formu
        JPanel formPanel = createFormPanel();
        
        // Butonlar
        JPanel buttonPanel = createButtonPanel();
        
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(40));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);
        
        add(mainPanel);
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        
        styleTextField(usernameField, "Müşteri ID");
        styleTextField(passwordField, "Şifre");
        
        panel.add(createInputPanel("Müşteri ID:", usernameField));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createInputPanel("Şifre:", passwordField));
        
        return panel;
    }
    
    private JPanel createInputPanel(String labelText, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        
        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(300, 30));
        
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(field);
        
        return panel;
    }
    
    private void styleTextField(JTextField field, String placeholder) {
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        
        JButton loginButton = createStyledButton("Giriş Yap");
        JButton registerButton = createStyledButton("Yeni Hesap Oluştur");
        
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> handleRegister());
        
        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(registerButton);
        
        return panel;
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 35));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 120, 215));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFocusPainted(false);
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 100, 195));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 120, 215));
            }
        });
        
        return button;
    }
    
    private void handleLogin() {
        String customerId = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        Customer customer = bankService.authenticateCustomer(customerId, password);
        if (customer != null) {
            MainFrame mainFrame = new MainFrame(bankService, customer);
            mainFrame.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Geçersiz müşteri ID veya şifre!",
                "Giriş Hatası",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleRegister() {
        JDialog dialog = new JDialog(this, "Yeni Müşteri Kaydı", true);
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Form alanları
        JTextField tcField = new JTextField(20);
        JTextField nameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JPasswordField confirmPasswordField = new JPasswordField(20);
        JTextField phoneField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextField emailField = new JTextField(20);

        // Form düzeni
        panel.add(new JLabel("TC Kimlik No:"));
        panel.add(Box.createVerticalStrut(5));
        panel.add(tcField);
        panel.add(Box.createVerticalStrut(15));

        panel.add(new JLabel("Ad Soyad:"));
        panel.add(Box.createVerticalStrut(5));
        panel.add(nameField);
        panel.add(Box.createVerticalStrut(15));

        panel.add(new JLabel("Şifre:"));
        panel.add(Box.createVerticalStrut(5));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(15));

        panel.add(new JLabel("Şifre Tekrar:"));
        panel.add(Box.createVerticalStrut(5));
        panel.add(confirmPasswordField);
        panel.add(Box.createVerticalStrut(15));

        panel.add(new JLabel("Telefon:"));
        panel.add(Box.createVerticalStrut(5));
        panel.add(phoneField);
        panel.add(Box.createVerticalStrut(15));

        panel.add(new JLabel("Adres:"));
        panel.add(Box.createVerticalStrut(5));
        panel.add(addressField);
        panel.add(Box.createVerticalStrut(15));

        panel.add(new JLabel("E-posta:"));
        panel.add(Box.createVerticalStrut(5));
        panel.add(emailField);
        panel.add(Box.createVerticalStrut(20));

        JButton registerButton = new JButton("Kayıt Ol");
        styleButton(registerButton);
        registerButton.addActionListener(e -> {
            String tcNo = tcField.getText();
            String name = nameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String phone = phoneField.getText();
            String address = addressField.getText();
            String email = emailField.getText();

            // Validasyonlar
            if (!Customer.validateTCNo(tcNo)) {
                JOptionPane.showMessageDialog(dialog,
                    "Geçersiz TC Kimlik Numarası!",
                    "Hata",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (name.trim().isEmpty() || password.trim().isEmpty() || phone.trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                    "Lütfen tüm zorunlu alanları doldurun!",
                    "Hata",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(dialog,
                    "Şifreler eşleşmiyor!",
                    "Hata",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            Customer customer = new Customer(tcNo, name, password, phone, address, email);
            bankService.addCustomer(customer);
            
            // Otomatik olarak bir vadesiz hesap aç
            bankService.createAccount(customer, "Checking");

            JOptionPane.showMessageDialog(dialog,
                "Kaydınız başarıyla oluşturuldu!\nTC Kimlik numaranız ile giriş yapabilirsiniz.",
                "Kayıt Başarılı",
                JOptionPane.INFORMATION_MESSAGE);
            
            dialog.dispose();
            
            // Giriş alanlarını otomatik doldur
            usernameField.setText(tcNo);
            this.passwordField.setText(password);
        });

        panel.add(registerButton);
        dialog.add(new JScrollPane(panel));
        dialog.setVisible(true);
    }
    
    private void styleButton(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 35));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 120, 215));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFocusPainted(false);
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(0, 100, 195));
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(0, 120, 215));
            }
        });
    }
} 