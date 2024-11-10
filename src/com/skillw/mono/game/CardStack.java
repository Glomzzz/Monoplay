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

    public CardStack() {
        this.cards = new Card[SIZE];
        // 20
        repeatAdd(Money.ONE,6);
        repeatAdd(Money.TWO,5);
        repeatAdd(Money.THREE,3);
        repeatAdd(Money.FOUR,3);
        repeatAdd(Money.FIVE,2);
        repeatAdd(Money.TEN,1);

        // 39
        repeatAdd(Property.BLACK,4);
        repeatAdd(Property.BLUE,2);
        repeatAdd(Property.BROWN,2);
        repeatAdd(Property.LIGHT_GREEN,2);
        repeatAdd(Property.LIGHT_BLUE,3);
        repeatAdd(Property.GREEN,3);
        repeatAdd(Property.ORANGE,3);
        repeatAdd(Property.PINK,3);
        repeatAdd(Property.RED,3);
        repeatAdd(Property.YELLOW,3);

        repeatAdd(Property.PINK_N_ORANGE,2);
        repeatAdd(Property.RED_N_YELLOW,2);
        add(Property.BLACK_N_GREEN);
        add(Property.BLACK_N_LIGHT_BLUE);
        add(Property.BLACK_N_LIGHT_GREEN);
        add(Property.BLUE_N_GREEN);
        add(Property.BROWN_N_LIGHT_BLUE);
        repeatAdd(Property.UNIVERSAL,2);

        // 13
        repeatAdd(Rent.BLACK_N_LIGHT_GREEN,2);
        repeatAdd(Rent.BLUE_N_GREEN,2);
        repeatAdd(Rent.BROWN_N_LIGHT_BLUE,2);
        repeatAdd(Rent.PINK_N_ORANGE,2);
        repeatAdd(Rent.RED_N_YELLOW,2);

        repeatAdd(RentUniversal.RENT_UNIVERSAL,3);

        // 29
        repeatAdd(PassGo.PASS_GO,10);
        repeatAdd(DoubleTheRent.DOUBLE_THE_RENT,2);
        repeatAdd(Birthday.BIRTHDAY,3);
        repeatAdd(ForcedDeal.FORCED_DEAL,3);
        repeatAdd(SlyDeal.SLY_DEAL,3);
        repeatAdd(DebtCollector.DEBT_COLLECTOR,3);
        repeatAdd(No.NO,3);
        repeatAdd(DealBreaker.DEAL_BREAKER,2);
    }

    private CardStack(int capacity) {
        this.cards = new Card[capacity];
    }

    private int nextTail(){
        return (tail++) % cards.length;
    }
    private int nextHead(){
        return (head++) % cards.length;
    }


    public void add(Card card) {
        this.cards[nextTail()] = card;
    }

    public void repeatAdd(Card card, int times){
        for(int i = 0; i < times; i++){
            add(card);
        }
    }

    public void shuffle(){
        for(int i = 0; i < SIZE; i++){
            int randomIndex = (int)(Math.random() * SIZE);
            Card temp = cards[i];
            cards[i] = cards[randomIndex];
            cards[randomIndex] = temp;
        }
    }

    public Card draw(){
        int head = nextHead();
        Card card = cards[head];
        cards[head] = null;
        return card;
    }
}
