package database;

import entity.Card;
import enums.CardStatus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReaderDatabase {
    public static List<Card> readCardsFromFile(String filename) {
        List<Card> cards = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 4) {
                    cards.add(new Card(parts[0], parts[1], Double.parseDouble(parts[2]), CardStatus.valueOf(parts[3])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return cards;
    }
}
