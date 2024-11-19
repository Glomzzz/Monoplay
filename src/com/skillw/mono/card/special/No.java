package com.skillw.mono.card.special;

import com.skillw.mono.card.Card;


/**
 * When player has been asked to do something,
 * Game will ask the player if they want to avoid it,
 * if the player has No card.
 */
public class No extends Card {

    public static final No NO = new No();

    //=============== Constructor =================
    //DEVELOPED BY: MORRO
    private No() {
        super("Just Say NO!",4);
    }
}
