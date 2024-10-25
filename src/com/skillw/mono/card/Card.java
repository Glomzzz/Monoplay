package com.skillw.mono.card;



public abstract class Card {

    /**
     * Card's name
     */
    private final String name;
    //  per million
    private final int worth;

    public Card(String name,int worth) {
        this.name = name;
        this.worth = worth;
    }
    public Money asMoney() {
        if (this instanceof Money) {
            return (Money) this;
        }
        return new Money(name, worth);
    }

    public int getWorth() {
        return worth;
    }

    public String getName() {
        return name;
    }
}
