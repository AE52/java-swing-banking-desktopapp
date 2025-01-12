package src.services;

import java.util.HashMap;
import java.util.ArrayList;
import src.models.*;
import src.interfaces.BankingOperations;
import java.util.List;

public class BankService implements BankingOperations {
    private HashMap<String, Customer> customers;
    private ArrayList<Account> accounts;

    public BankService() {
        customers = new HashMap<>();
        accounts = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    public Customer getCustomer(String customerId) {
        return customers.get(customerId);
    }

    private Account findAccountByNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public boolean transferByAccountNumbers(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = findAccountByNumber(fromAccountNumber);
        Account toAccount = findAccountByNumber(toAccountNumber);
        
        if (fromAccount != null && toAccount != null) {
            return transfer(fromAccount, toAccount, amount);
        }
        return false;
    }

    @Override
    public boolean transfer(Account from, Account to, double amount) {
        if (from.withdraw(amount)) {
            to.deposit(amount);
            return true;
        }
        return false;
    }

    @Override
    public void createAccount(Customer customer, String accountType) {
        String accountNumber = generateAccountNumber();
        Account account;
        
        if (accountType.equals("Savings")) {
            account = new SavingsAccount(accountNumber, customer, 0.05);
        } else {
            account = new CheckingAccount(accountNumber, customer);
        }
        
        customer.addAccount(account);
        accounts.add(account);
    }

    @Override
    public ArrayList<Account> getCustomerAccounts(String customerId) {
        Customer customer = customers.get(customerId);
        return customer != null ? customer.getAccounts() : new ArrayList<>();
    }

    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis();
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    public boolean transferToCustomer(String fromAccountNumber, String toCustomerId, double amount) {
        Account fromAccount = findAccountByNumber(fromAccountNumber);
        Customer toCustomer = customers.get(toCustomerId);
        
        if (fromAccount != null && toCustomer != null && toCustomer.getMainAccount() != null) {
            return transfer(fromAccount, toCustomer.getMainAccount(), amount);
        }
        return false;
    }

    public Customer authenticateCustomer(String customerId, String password) {
        Customer customer = customers.get(customerId);
        if (customer != null && customer.validatePassword(password)) {
            return customer;
        }
        return null;
    }

    public Customer findCustomerByName(String name) {
        for (Customer customer : customers.values()) {
            if (customer.getName().equalsIgnoreCase(name)) {
                return customer;
            }
        }
        return null;
    }
} 