package entity;

import enums.CardStatus;

import java.io.Serializable;

public class Card implements Serializable {
    private String cardNumber;
    private String pinCode;
    private double balance;
    private CardStatus cardStatus;


    public Card(String cardNumber, String pinCode, double balance, CardStatus cardStatus) {
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
        this.balance = balance;
        this.cardStatus = cardStatus;
    }


    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardNumber='" + cardNumber + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", balance=" + balance +
                ", cardStatus=" + cardStatus +
                '}';
    }
}
