package service;

import database.ReaderDatabase;
import entity.Card;
import enums.CardStatus;
import exception.CardNotFoundException;
import exception.InvalidFormatException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static common.FilePaths.DATA_FILE_LOCATION;

public class CardService {
    private static List<Card> cards = ReaderDatabase.readCardsFromFile(DATA_FILE_LOCATION);


    public Card enterCardNumber(String cardNumber) throws CardNotFoundException, InvalidFormatException {
        if (!CardValidator.isFormattedCardNumber(cardNumber)) {
            throw new InvalidFormatException("Номер карты введен в неправильном формате. Попробуйте ещё раз");
        }

        for (Card card : cards) {
            if (card.getCardNumber().equals(cardNumber))
                return card;
        }

        throw new CardNotFoundException("Карточка с номером" + cardNumber + "не найдена");
    }

    public Boolean enterPinCode(Card card, String pinCode) throws InvalidFormatException {
        if (!CardValidator.isFormattedPinCode(pinCode)) {
            throw new InvalidFormatException("Пин-код в неверном формате. Попробуйте ещё раз");
        }
        return card.getPinCode().equals(pinCode);
    }

    public void blockCard(Card card) throws IOException {
        card.setCardStatus(CardStatus.BLOCKED);
        updateDatabase();
    }

    public void updateDatabase() throws IOException {
        try (FileWriter writer = new FileWriter(DATA_FILE_LOCATION)) {
            for (Card c : cards) {
                writer.write(c.getCardNumber() + " " + c.getPinCode() + " " + c.getBalance() + " " + c.getCardStatus() + "\n");
            }
        }
    }

}
