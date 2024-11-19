package com.skillw.mono.game;

import com.skillw.mono.card.Card;
import com.skillw.mono.card.Money;
import com.skillw.mono.card.Property;
import com.skillw.mono.card.action.*;

/**
 * Cards on the table
 */
public class CardStack {

    private final Card[] cards;
    private int head = 0; // ++ draw from here
    private int tail = 0; // ++ add to here
    private static final int SIZE = 101;

    //=============== Constructor =================
    //DEVELOPED BY: GLOM
    public CardStack() {
        this.cards = new Card[SIZE];
        // 20
        add(Money.ONE,6);
        add(Money.TWO,5);
        add(Money.THREE,3);
        add(Money.FOUR,3);
        add(Money.FIVE,2);
        add(Money.TEN,1);

        // 39
        add(Property.BLACK,4);
        add(Property.BLUE,2);
        add(Property.BROWN,2);
        add(Property.LIGHT_GREEN,2);
        add(Property.LIGHT_BLUE,3);
        add(Property.GREEN,3);
        add(Property.ORANGE,3);
        add(Property.PINK,3);
        add(Property.RED,3);
        add(Property.YELLOW,3);

        add(Property.PINK_N_ORANGE,2);
        add(Property.RED_N_YELLOW,2);
        add(Property.BLACK_N_GREEN);
        add(Property.BLACK_N_LIGHT_BLUE);
        add(Property.BLACK_N_LIGHT_GREEN);
        add(Property.BLUE_N_GREEN);
        add(Property.BROWN_N_LIGHT_BLUE);
        add(Property.UNIVERSAL,2);

        // 13
        add(Rent.BLACK_N_LIGHT_GREEN,2);
        add(Rent.BLUE_N_GREEN,2);
        add(Rent.BROWN_N_LIGHT_BLUE,2);
        add(Rent.PINK_N_ORANGE,2);
        add(Rent.RED_N_YELLOW,2);

        add(RentUniversal.RENT_UNIVERSAL,3);

        // 29
        add(PassGo.PASS_GO,10);
        add(DoubleTheRent.DOUBLE_THE_RENT,2);
        add(Birthday.BIRTHDAY,3);
        add(ForcedDeal.FORCED_DEAL,3);
        add(SlyDeal.SLY_DEAL,3);
        add(DebtCollector.DEBT_COLLECTOR,3);
        add(No.NO,3);
        add(DealBreaker.DEAL_BREAKER,2);
    }

    //DEVELOPED BY: GLOM
    private int nextTail(){
        return (tail++) % cards.length;
    }
    //DEVELOPED BY: GLOM
    private int nextHead(){
        return (head++) % cards.length;
    }

    //DEVELOPED BY: GLOM
    /**
     * Add card once
     *
     * @param card  the card to be added
     */
    public void add(Card card) {
        int index = nextTail();
        this.cards[index] = card;
    }

    //DEVELOPED BY: GLOM
    /**
     * Add card repeatedly
     *
     * @param card  the card to be added
     * @param times number of times to add the card
     */
    public void add(Card card, int times){
        for(int i = 0; i < times; i++){
            add(card);
        }
    }

    //DEVELOPED BY: GLOM
    /**
     * shuffle the card
     */
    public void shuffle(){
        for(int i = 0; i < SIZE; i++){
            int randomIndex = (int)(Math.random() * SIZE);
            Card temp = cards[i];
            cards[i] = cards[randomIndex];
            cards[randomIndex] = temp;
        }
    }

    //DEVELOPED BY: GLOM
    public Card draw(){
        int head = nextHead();
        Card card = cards[head];
        cards[head] = null;
        return card;
    }
}
