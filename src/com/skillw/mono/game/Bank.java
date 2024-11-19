package com.skillw.mono.game;

import com.skillw.mono.card.Card;
import com.skillw.mono.card.Money;
import com.skillw.mono.util.CardCounter;

/**
 * Where to store and withdraw money
 */
public class Bank extends CardCounter {

    public static final Money[] MONEYS = new Money[]{
            Money.ONE,Money.TWO,Money.THREE,Money.FOUR, Money.FIVE, Money.TEN
    };

    //=============== Constructor =================
    //DEVELOPED BY: MORRO
    public Bank() {
        super(MONEYS.length);
    }
    /**
     * Calculate the sum of the money in bank
     *
     * @return the command to be executed
     */
    public int calculateTotalWorth(){
        int sum = 0;
        for (int i = 0; i < MONEYS.length; i++) {
            sum += getNumOf(i) * MONEYS[i].getWorth();
        }
        return sum;
    }

    /**
     * Get the card's
     *
     * @param card  card
     * @return      the command to be executed
     */
    public int getIndexOf(Card card) {
        for (int i = 0; i < MONEYS.length; i++) {
            if (MONEYS[i].equals(card)) {
                return i;
            }
        }
        return -1;
    }

    public Card getCardOf(int index) {
        return MONEYS[index];
    }
}
