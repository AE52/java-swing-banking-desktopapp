package src;

import javax.swing.SwingUtilities;
import src.gui.LoginFrame;
import src.services.BankService;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankService bankService = new BankService();
            LoginFrame loginFrame = new LoginFrame(bankService);
            loginFrame.setVisible(true);
        });
    }
} 