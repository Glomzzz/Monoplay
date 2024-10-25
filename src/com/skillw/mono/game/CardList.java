package com.skillw.mono.game;

import com.skillw.mono.card.Card;
import com.skillw.mono.card.Money;
import com.skillw.mono.card.PerformableCard;
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
    public static final int PROPERTY_END = 23;
    public static final int RENT_START = 23;
    public static final int RENT_END = 28;
    public static final int MONEY_START = 28;
    public static final int MONEY_END = 32;
    public static final PerformableCard[] CARDS = new PerformableCard[]{
            PassGo.PASS_GO,                                   // 0
            DealBreaker.DEAL_BREAKER,
            ForcedDeal.FORCED_DEAL,
            DebtCollector.DEBT_COLLECTOR,
            SlyDeal.SLY_DEAL,
            Property.UNIVERSAL,
            Property.RED,                                     // 6
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
            Rent.BLACK_N_LIGHT_GREEN,                         // 23
            Rent.BLUE_N_GREEN,
            Rent.BROWN_N_LIGHT_BLUE,
            Rent.PINK_N_ORANGE,
            Rent.RED_N_YELLOW,
            Money.ONE,                                        // 28
            Money.TWO,
            Money.THREE,
            Money.FIVE,
            Money.TEN                                         // 32
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
