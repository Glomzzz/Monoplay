package com.skillw.mono.game;

import com.skillw.mono.card.Card;
import com.skillw.mono.card.Money;
public class Player {
    private final String name;
    private final Bank bank;
    private final CardList cardList;
    private final PropertyList propertyList;

    public Player(String name) {
        this.name = name;
        this.bank = new Bank();
        this.cardList = new CardList();
        this.propertyList = new PropertyList();
    }

    public String getName() {
        return name;
    }

    public Bank getBank() {
        return bank;
    }

    public CardList getCardList() {
        return cardList;
    }

    public PropertyList getPropertyList() {
        return propertyList;
    }

    public void recieveCard(Card card){
        if (card instanceof Money){
            bank.add(card);
        } else {
            cardList.add(card);
        }
    }

}
