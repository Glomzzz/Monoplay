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

    public Bank() {
        super(MONEYS.length);
    }


    public int calculateWorth(){
        int sum = 0;
        for (int i = 0; i < MONEYS.length; i++) {
            sum += getNumOf(i) * MONEYS[i].getWorth();
        }
        return sum;
    }

    public int getIndexOf(Card card) {
        for (int i = 0; i < MONEYS.length; i++) {
            if (MONEYS[i].equals(card)) {
                return i;
            }
        }
        return -1;
    }
}
