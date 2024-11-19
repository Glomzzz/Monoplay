package com.skillw.mono.util;

import com.skillw.mono.card.Card;

public abstract class CardCounter {
    private final int[] cards;
    private int size = 0;

    //=============== Constructor =================
    //DEVELOPED BY: MORRO
    public CardCounter(int size) {
        this.cards = new int[size];
    }

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

    /**
     * Get the amount of card
     *
     * @param index index of the card
     * @return      number of card
     */
    public int getNumOf(int index){
        return cards[index];
    }

    /**
     * Get the amount of card
     *
     * @param card  card
     * @return      number of card
     */
    public int getNumOf(Card card) {
        return getNumOf(getIndexOf(card));
    }

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

    /**
     * Clear the cards
     *
     * @param card card
     * @return the number cards cleared
     */
    public int clearOf(Card card) {
        return clearOf(getIndexOf(card));
    }

    /**
     * Take a card
     *
     * @param index index of the card
     */
    private void take(int index) {
        cards[index] = cards[index] - 1;
        size--;
    }

    /**
     * Take a card
     *
     * @param card the card
     */
    public void take(Card card) {
        take(getIndexOf(card));
    }

    /**
     * Get the size
     * @return
     */
    public int getSize(){
        return size;
    }

    /**
     * Get the amount of card based on the index of the card
     *
     * @param   card the card
     * @return  the card's index
     */
    public abstract int getIndexOf(Card card);

    /**
     * Get the card based on index
     *
     * @param index index of the card
     * @return the card
     */
    public abstract Card getCardOf(int index);
}
