package com.skillw.mono.game;

import com.skillw.mono.card.Card;

/**
 * Cards on the player's hand
 */
public class CardList {
    private Card[] cards;
    private int capacity;
    private int size;


    public CardList() {
        this.capacity = 15;
        this.cards = new Card[this.capacity];
    }

    private CardList(int capacity) {
        this.cards = new Card[capacity];
    }

    private void resize(){
        this.capacity = (int) (this.capacity * 1.5);
        Card[] newArray = new Card[this.capacity];
        for(int i = 0; i < cards.length; i++){
            newArray[i] = this.cards[i];
        }
        this.cards = newArray;
    }

    public void add(Card card) {
        if(this.size == this.capacity) resize();
        this.cards[this.size++] = card;
    }

    public Card get(int index){
        return cards[index];
    }

    public int size(){
        return size;
    }
    public void sort(){
       // sorted by name
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (cards[i].getName().compareTo(cards[j].getName()) > 0) {
                    Card temp = cards[i];
                    cards[i] = cards[j];
                    cards[j] = temp;
                }
            }
        }
    }


}
