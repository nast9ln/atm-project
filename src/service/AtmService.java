package service;

import entity.Card;
import exception.CardNotFoundException;
import exception.InvalidFormatException;

import java.io.IOException;
import java.util.Scanner;

public class AtmService {
    private final CardDepositor cardDepositor;
    private final CardService cardService;

    private static final Integer ATTEMPTS_LEFT = 3;

    public AtmService(CardDepositor cardDepositor, CardService cardService) {
        this.cardDepositor = cardDepositor;
        this.cardService = cardService;
    }

    public void workWithAtm() throws IOException {
        Card card = getCard();

        if (CardValidator.isBlockedCard(card.getCardStatus())) {
            System.out.println("Карта заблокирована. Доступ запрещён.");
            return;
        }

        checkPinCode(card);

        menu(card);
    }

    private Card getCard() {
        System.out.println("Введите номер карты в формате «ХХХХ-ХХХХ-ХХХХ-ХХХХ:");
        Card card = null;
        while (card == null) {
            try {
                Scanner scanner = new Scanner(System.in);
                String cardNum = scanner.nextLine();
                card = cardService.enterCardNumber(cardNum);
            } catch (CardNotFoundException | InvalidFormatException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return card;
    }

    private void checkPinCode(Card card) {
        System.out.println("Введите пин-код в формате XXXX:");

        int currentTry = ATTEMPTS_LEFT;
        while (currentTry > 0) {
            try {
                Scanner scanner = new Scanner(System.in);
                String pinCode = scanner.nextLine();
                Boolean correct = cardService.enterPinCode(card, pinCode);
                if (correct) {
                    break;
                }
                currentTry--;
                if (currentTry == 0) {
                    cardService.blockCard(card);
                }
                System.out.println("Неверный пин-код.  Осталось попыток " + currentTry);
            } catch (InvalidFormatException ex) {
                System.out.println(ex.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void menu(Card card) throws IOException {
        if (card != null) {
            while (true) {
                System.out.println("\nМеню:");
                System.out.println("1. Показать баланс");
                System.out.println("2. Снять деньги");
                System.out.println("3. Пополнить баланс");
                System.out.println("4. Выход");

                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Баланс карты: " + card.getBalance());
                        break;
                    case 2:
                        System.out.print("Введите сумму для снятия: ");
                        double amountToWithdraw = scanner.nextDouble();
                        cardDepositor.withdrawMoney(card, amountToWithdraw);
                        break;
                    case 3:
                        System.out.print("Введите сумму для пополнения: ");
                        double amountToDeposit = scanner.nextDouble();
                        cardDepositor.depositMoney(card, amountToDeposit);
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Неправильный выбор. Пожалуйста, введите номер операции из меню.");
                }
            }
        }
    }
}
