package src.models;

public class CheckingAccount extends Account {
    public CheckingAccount(String accountNumber, Customer owner) {
        super(accountNumber, owner);
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
} 