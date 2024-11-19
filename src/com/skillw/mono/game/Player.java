package com.skillw.mono.game;

import com.skillw.mono.card.Card;
import com.skillw.mono.card.Money;
public class Player {

    private final String name;
    private final Bank bank;
    private final CardList cardList;
    private final PropertyList propertyList;

    //=============== Constructor =================
    //DEVELOPED BY: GLOM
    public Player(String name, CardStack stack) {
        this.name = name;
        this.bank = new Bank();
        this.cardList = new CardList(stack);
        this.propertyList = new PropertyList();
    }

    //DEVELOPED BY: MORRO
    /**
     * Get the name of the player
     *
     * @return player's name
     */
    public String getName() {
        return name;
    }

    //DEVELOPED BY: MORRO
    /**
     * Get the player's bank
     *
     * @return the player's bank
     */
    public Bank getBank() {
        return bank;
    }

    //DEVELOPED BY: MORRO
    /**
     * Get the cards on player's hand
     *
     * @return the CardList
     */
    public CardList getCardList() {
        return cardList;
    }

    //DEVELOPED BY: MORRO
    /**
     * Get the properties player has on the table
     *
     * @return  the propertyList
     */
    public PropertyList getPropertyList() {
        return propertyList;
    }

    //DEVELOPED BY: MORRO
    /**
     * Receive card to the player
     *
     * @param card     the card player will receive
     */
    public void recieveCard(Card card){
        if (card instanceof Money){
            bank.add(card);
        } else {
            cardList.add(card);
        }
    }
}
