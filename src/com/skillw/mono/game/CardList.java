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
    public static final Card[] CARDS = new Card[]{
            PassGo.PASS_GO,
            DealBreaker.DEAL_BREAKER,
            ForcedDeal.FORCED_DEAL,
            DebtCollector.DEBT_COLLECTOR,
            SlyDeal.SLY_DEAL,
            Property.UNIVERSAL,
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
            DoubleTheRent.DOUBLE_THE_RENT,
            Rent.BLACK_N_LIGHT_GREEN,
            Rent.BLUE_N_GREEN,
            Rent.BROWN_N_LIGHT_BLUE,
            Rent.PINK_N_ORANGE,
            Rent.RED_N_YELLOW,
            Money.ONE,
            Money.TWO,
            Money.THREE,
            Money.FIVE,
            Money.TEN
    };


    public CardList() {
        super(CARDS.length);
    }

    public int getIndexOf(Card card) {
        for (int i = 0; i < CARDS.length; i++) {
            if (CARDS[i].equals(card)) {
                return i;
            }
        }
        return -1;
    }

    public int calculateWorth(){
        int sum = 0;
        for (int i = 0; i < size(); i++) {
            sum += getNumOf(i) * CARDS[i].getWorth();
        }
        return sum;
    }


}
