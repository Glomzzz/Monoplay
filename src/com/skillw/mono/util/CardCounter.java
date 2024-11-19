package com.skillw.mono.util;

import com.skillw.mono.card.Card;

public abstract class CardCounter {
    private final int[] cards;
    private int size = 0;

    //=============== Constructor =================
    //DEVELOPED BY: GLOM
    public CardCounter(int size) {
        this.cards = new int[size];
    }

    //DEVELOPED BY: GLOM
    /**
     * Add the card to the card counter
     *
     * @param card  card
     */
    public void add(Card card) {
        int index = getIndexOf(card);
        cards[index] = cards[index] + 1;
        size++;
    }

    //DEVELOPED BY: GLOM
    /**
     * Get the amount of card
     *
     * @param index index of the card
     * @return      number of card
     */
    public int getNumOf(int index){
        return cards[index];
    }

    //DEVELOPED BY: GLOM
    /**
     * Get the amount of card
     *
     * @param card  card
     * @return      number of card
     */
    public int getNumOf(Card card) {
        return getNumOf(getIndexOf(card));
    }

    //DEVELOPED BY: GLOM
    /**
     * Clear the cards
     *
     * @param index of the card
     * @return the number cards cleared
     */
    public int clearOf(int index){
        int num = cards[index];
        cards[index] = 0;
        size -= num;
        return num;
    }

    //DEVELOPED BY: GLOM
    /**
     * Clear the cards
     *
     * @param card card
     * @return the number cards cleared
     */
    public int clearOf(Card card) {
        return clearOf(getIndexOf(card));
    }

    //DEVELOPED BY: GLOM
    /**
     * Take a card
     *
     * @param index index of the card
     */
    private void take(int index) {
        cards[index] = cards[index] - 1;
        size--;
    }

    //DEVELOPED BY: GLOM
    /**
     * Take a card
     *
     * @param card the card
     */
    public void take(Card card) {
        take(getIndexOf(card));
    }

    //DEVELOPED BY: GLOM
    /**
     * Get the size
     * @return the size
     */
    public int getSize(){
        return size;
    }

    //DEVELOPED BY: GLOM
    /**
     * Get the index of the card
     *
     * @param   card the card
     * @return  the card's index
     */
    public abstract int getIndexOf(Card card);

    //DEVELOPED BY: GLOM
    /**
     * Get the card
     *
     * @param index index of the card
     * @return the card
     */
    public abstract Card getCardOf(int index);
}
