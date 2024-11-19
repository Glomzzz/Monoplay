package com.skillw.mono.game;

import com.skillw.mono.card.Card;
import com.skillw.mono.card.Money;
import com.skillw.mono.card.Property;
import com.skillw.mono.card.action.*;
import com.skillw.mono.util.CardCounter;

/**
 * Cards on the player's hand
 */
public class CardList extends CardCounter{

    public static final int ACTION_START = 0;
    public static final int ACTION_END = 6;
    public static final int PROPERTY_START = 6;
    public static final int PROPERTY_END = 24;
    public static final int RENT_START = 24;
    public static final int RENT_END = 30;
    public static final Card[] CARDS = new Card[]{
            PassGo.PASS_GO,                                   // 0
            DealBreaker.DEAL_BREAKER,
            ForcedDeal.FORCED_DEAL,
            DebtCollector.DEBT_COLLECTOR,
            SlyDeal.SLY_DEAL,
            Birthday.BIRTHDAY,
            Property.UNIVERSAL,                              // 6
            Property.RED,
            Property.BLUE,
            Property.PINK,
            Property.GREEN,
            Property.YELLOW,
            Property.BLACK,
            Property.ORANGE,
            Property.BROWN,
            Property.LIGHT_BLUE,
            Property.LIGHT_GREEN,
            Property.BLACK_N_GREEN,
            Property.BLACK_N_LIGHT_GREEN,
            Property.BLACK_N_LIGHT_BLUE,
            Property.BLUE_N_GREEN,
            Property.BROWN_N_LIGHT_BLUE,
            Property.PINK_N_ORANGE,
            Property.RED_N_YELLOW,
            Rent.BLACK_N_LIGHT_GREEN,                         // 24
            Rent.BLUE_N_GREEN,
            Rent.BROWN_N_LIGHT_BLUE,
            Rent.PINK_N_ORANGE,
            Rent.RED_N_YELLOW,
            RentUniversal.RENT_UNIVERSAL,
            Money.ONE,                                        // 30
            Money.TWO,
            Money.THREE,
            Money.FOUR,
            Money.FIVE,
            Money.TEN,
            No.NO,                                            // 36
            DoubleTheRent.DOUBLE_THE_RENT
    };

    private CardStack cardStack;

    //=============== Constructor =================
    public CardList(CardStack cardStack) {
        super(CARDS.length);
        this.cardStack = cardStack;
    }

    /**
     * Perform the card's action
     *
     * @param card     the card
     * @return the command to be executed
     */
    public int getIndexOf(Card card) {
        for (int i = 0; i < CARDS.length; i++) {
            if (CARDS[i].equals(card)) {
                return i;
            }
        }
        return -1; 
    }

    /**
     * Perform the card's action
     *
     * @param card     the card
     * @return the command to be executed
     */
    public void consume(Card card){
        take(card);
        cardStack.add(card);
    }

    /**
     * Get the total worth of the cards on hand
     *
     * @return the command to be executed
     */
    public int calculateTotalWorth(){
        int sum = 0;
        for (int i = 0; i < size(); i++) {
            sum += getNumOf(i) * CARDS[i].getWorth();
        }
        return sum;
    }

    /**
     * Draw the card
     */
    public void draw(){
        add(cardStack.draw());
    }

    public int getNumOfNo(){
        return getNumOf(36);
    }

    public int getNumOfDoubleRent(){
        return getNumOf(37);
    }
}
