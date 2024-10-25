package com.skillw.mono.game;

import com.skillw.mono.card.Card;
import com.skillw.mono.card.Money;
import com.skillw.mono.util.CardCounter;

/**
 * Where to store and withdraw money
 */
public class Bank extends CardCounter {
    public static final Money[] MONIES = new Money[]{
            Money.ONE,Money.TWO,Money.THREE, Money.FIVE, Money.TEN
    };

    public Bank() {
        super(MONIES.length);
    }


    public int calculateWorth(){
        int sum = 0;
        for (int i = 0; i < size(); i++) {
            sum += getNumOf(i) * MONIES[i].getWorth();
        }
        return sum;
    }

    public int getIndexOf(Card card) {
        for (int i = 0; i < MONIES.length; i++) {
            if (MONIES[i].equals(card)) {
                return i;
            }
        }
        return -1;
    }
}
