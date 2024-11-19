package com.skillw.mono.card;

public abstract class Card {

    private final String name;  // Card's name
    private final int worth;    //  per million

    //=============== Constructor =================
    //DEVELOPED BY: MORRO
    public Card(String name,int worth) {
        this.name = name;
        this.worth = worth;
    }

    //DEVELOPED BY: MORRO
    /**
     * Find the card's worth
     *
     * @return the worth of the card
     */
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

    //DEVELOPED BY: MORRO
    /**
     * Get the worth of the card
     *
     * @return the worth of the card
     */
    public int getWorth() {
        return worth;
    }

    //DEVELOPED BY: MORRO
    /**
     * Get the name of the card
     *
     * @return the name of the card
     */
    public String getName() {
        return name;
    }
}
