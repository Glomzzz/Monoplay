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
        switch (worth){
            case 1:
                return Money.ONE;
            case 2:
                return Money.TWO;
            case 3:
                return Money.THREE;
            case 4:
                return Money.FOUR;
            case 5:
                return Money.FIVE;
            case 10:
                return Money.TEN;
            default:
                return null;
        }
    }

    public int getWorth() {
        return worth;
    }

    public String getName() {
        return name;
    }
}
