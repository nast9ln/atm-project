import database.ReaderDatabase;
import entity.Card;
import service.AtmDepositor;
import service.AtmService;
import service.CardDepositor;
import service.CardService;

import java.io.IOException;
import java.util.List;

public class Runner {

    public static void main(String[] args) throws IOException {
        List<Card> cards = ReaderDatabase.readCardsFromFile("data.txt");
        for (Card card : cards)
            System.out.println(card.toString());

        CardService cardService = new CardService();
        AtmDepositor atmDepositor = new AtmDepositor();
        CardDepositor cardDepositor = new CardDepositor(cardService, atmDepositor);
        try {
            new AtmService(cardDepositor, cardService).workWithAtm();
        } catch (Exception e) {
            System.out.println("При работе банкомата произошла критическая ошибка. Попробуйте обратиться позже.");
            throw e;
        }
    }

}
