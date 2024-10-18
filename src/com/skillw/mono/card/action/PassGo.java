package com.skillw.mono.card.action;

import com.skillw.mono.game.Player;
import com.skillw.mono.game.State;

public class PassGo extends ActionCard {
    public static final PassGo PASS_GO = new PassGo();
    private PassGo() {
        super("Pass GO",1);
    }

    /**
     * Pass GO and draw 2 cards
     * @param state Game state
     * @param performer Player who performs the card
     */
    public void perform(State state, Player performer) {
    }
}
