package com.skillw.mono.card.action;

import com.skillw.mono.card.Card;


/**
 * When player has been asked to do something,
 * he can play this card to avoid the action.
 */
public class No extends Card {

    public static final No NO = new No();

    private No() {
        super("Just Say NO!",4);
    }
}
