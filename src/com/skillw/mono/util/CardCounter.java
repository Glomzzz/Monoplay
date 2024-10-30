package com.skillw.mono.util;

import com.skillw.mono.card.Card;

public abstract class CardCounter {
    private int[] cards;


    public CardCounter(int size) {
        this.cards = new int[size];
    }

    public abstract int getIndexOf(Card card);

    public void add(Card card) {
        int index = getIndexOf(card);
        cards[index] = cards[index] + 1;
    }

    public boolean hasCardOf(int index){
        return cards[index] > 0;
    }
    public boolean hasCardOf(Card card){
        return hasCardOf(getIndexOf(card));
    }

    public int getNumOf(int index){
        return cards[index];
    }
    public int getNumOf(Card card) { return getNumOf(getIndexOf(card)); }

    public int clearOf(int index){
        int num = cards[index];
        cards[index] = 0;
        return num;
    }
    public int clearOf(Card card) { return clearOf(getIndexOf(card)); }

    public void take(int index) {
        cards[index] = cards[index] - 1;
    }
    public void take(Card card) {
        take(getIndexOf(card));
    }

    public int size(){
        return cards.length;
    }
}
