package service;

import entity.Card;

import java.io.IOException;

public class CardDepositor {

    private final CardService cardService;
    private final AtmDepositor atmDepositor;

    public CardDepositor(CardService cardService, AtmDepositor atmDepositor) {
        this.cardService = cardService;
        this.atmDepositor = atmDepositor;
    }

    public void withdrawMoney(Card card, double amount) throws IOException {
        if (amount <= 0) {
            System.out.println("Сумма должна быть положительной.");
            return;
        }

        if (amount > 1_000_000) {
            System.out.println("Максимальная сумма для пополнения 1 млн.");
            return;
        }

        if (CardValidator.isBlockedCard(card.getCardStatus())) {
            System.out.println("Карта заблокирована.");
            return;
        }

        if (card.getBalance() < amount) {
            System.out.println("Недостаточно средств на счете.");
            return;
        }

        if (atmDepositor.readBalance() < amount) {
            System.out.println("Недостаточно средств в банкомате.");
            return;
        }

        atmDepositor.withdrawMoney(amount);
        card.setBalance(card.getBalance() - amount);
        cardService.updateDatabase();
        System.out.println("Вы успешно сняли " + amount + " средств. Новый баланс: " + card.getBalance());
    }

    public void depositMoney(Card card, double amount) throws IOException {
        if (amount <= 0) {
            System.out.println("Сумма должна быть положительной.");
            return;
        }

        if (CardValidator.isBlockedCard(card.getCardStatus())) {
            System.out.println("Карта заблокирована.");
            return;
        }

        atmDepositor.depositMoney(amount);
        card.setBalance(card.getBalance() + amount);
        cardService.updateDatabase();
        System.out.println("Вы успешно пополнили баланс на " + amount + " . Новый баланс: " + card.getBalance());
    }
}
