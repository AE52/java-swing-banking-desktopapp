package src.models;

public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, Customer owner, double interestRate) {
        super(accountNumber, owner);
        this.interestRate = interestRate;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            setBalance(getBalance() + amount);
        }
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && getBalance() >= amount) {
            setBalance(getBalance() - amount);
            return true;
        }
        return false;
    }

    public void applyInterest() {
        double interest = getBalance() * interestRate;
        setBalance(getBalance() + interest);
    }
} 