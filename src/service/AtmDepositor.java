package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static common.FilePaths.ATM_FILE_PATH;


public class AtmDepositor {

    public double readBalance() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ATM_FILE_PATH))) {
            String balanceStr = reader.readLine();
            return Double.parseDouble(balanceStr);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public void updateBalance(double newBalance) {
        try (FileWriter writer = new FileWriter(ATM_FILE_PATH)) {
            writer.write(Double.toString(newBalance));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void depositMoney(double amount) {
        double currentBalance = readBalance();
        double newBalance = currentBalance + amount;
        updateBalance(newBalance);
    }

    public void withdrawMoney(double amount) {
        double currentBalance = readBalance();
        if (currentBalance < amount) {
            System.out.println("Недостаточно средств в ATM.");
            return;
        }

        double newBalance = currentBalance - amount;
        updateBalance(newBalance);
    }
}