package service;

import enums.CardStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static common.RegexConstants.CARD_NUMBER_FORMAT;
import static common.RegexConstants.PINCODE_FORMAT;

public class CardValidator {
    public static boolean isFormattedCardNumber(String input) {
        Pattern pattern = Pattern.compile(CARD_NUMBER_FORMAT);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean isFormattedPinCode(String input) {
        Pattern pattern = Pattern.compile(PINCODE_FORMAT);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean isBlockedCard(CardStatus cardStatus) {
        return cardStatus.equals(CardStatus.BLOCKED);
    }

}
