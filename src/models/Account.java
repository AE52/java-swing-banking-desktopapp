package src.models;

public abstract class Account {
    private String accountNumber;
    private double balance;
    private Customer owner;

    public Account(String accountNumber, Customer owner) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = 1000.0;
    }

    // Getter ve Setter metodları
    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public Customer getOwner() { return owner; }

    protected void setBalance(double balance) { this.balance = balance; }

    // Abstract metodlar
    public abstract void deposit(double amount);
    public abstract boolean withdraw(double amount);
} 