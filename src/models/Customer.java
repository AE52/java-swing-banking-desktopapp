package src.models;

import java.util.ArrayList;

public class Customer {
    private String tcNo; // TC Kimlik No
    private String name;
    private String password;
    private ArrayList<Account> accounts;
    private Account mainAccount;
    private String phoneNumber; // Yeni eklenen
    private String address; // Yeni eklenen
    private String email; // Yeni eklenen

    public Customer(String tcNo, String name, String password, String phoneNumber, String address, String email) {
        this.tcNo = tcNo;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.accounts = new ArrayList<>();
    }

    // Getter metodları
    public String getId() { return tcNo; } // TC No artık ID olarak kullanılıyor
    public String getName() { return name; }
    public String getPassword() { return password; }
    public ArrayList<Account> getAccounts() { return accounts; }
    public Account getMainAccount() { return mainAccount; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }

    // Setter metodları
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setAddress(String address) { this.address = address; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    public void addAccount(Account account) {
        accounts.add(account);
        if (mainAccount == null) {
            mainAccount = account;
        }
    }

    public boolean validatePassword(String inputPassword) {
        return password.equals(inputPassword);
    }

    // TC Kimlik doğrulama metodu
    public static boolean validateTCNo(String tcNo) {
        if (tcNo == null || tcNo.length() != 11) return false;
        try {
            long number = Long.parseLong(tcNo);
            if (number <= 0) return false;
            
            int[] digits = new int[11];
            for (int i = 0; i < 11; i++) {
                digits[i] = Integer.parseInt(tcNo.substring(i, i + 1));
            }
            
            // TC Kimlik algoritması kontrolü
            int sum1 = 0, sum2 = 0;
            for (int i = 0; i < 9; i += 2) {
                sum1 += digits[i];
            }
            for (int i = 1; i < 8; i += 2) {
                sum2 += digits[i];
            }
            
            if ((sum1 * 7 - sum2) % 10 != digits[9]) return false;
            if ((sum1 + sum2 + digits[9]) % 10 != digits[10]) return false;
            
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
} 