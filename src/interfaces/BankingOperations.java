package src.interfaces;

import java.util.ArrayList;
import src.models.Account;
import src.models.Customer;

public interface BankingOperations {
    boolean transfer(Account from, Account to, double amount);
    void createAccount(Customer customer, String accountType);
    ArrayList<Account> getCustomerAccounts(String customerId);
} 