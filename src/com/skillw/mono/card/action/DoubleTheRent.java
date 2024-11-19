package com.skillw.mono.card.action;

import com.skillw.mono.card.Card;

/**
 * When player performs Rent card,
 * Game will ask the player if he wants to double the rent,
 * if the player has Double The Rent card.
 */
public class DoubleTheRent extends Card {

    public static final DoubleTheRent DOUBLE_THE_RENT = new DoubleTheRent();

    //=============== Constructor =================
    //DEVELOPED BY: MORRO
    private DoubleTheRent() {
        super("Double The Rent",1);
    }
}
