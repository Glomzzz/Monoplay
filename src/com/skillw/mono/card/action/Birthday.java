package com.skillw.mono.card.action;

import com.skillw.mono.game.Player;
import com.skillw.mono.game.State;


public class Birthday extends ActionCard {
    public static final Birthday BIRTHDAY = new Birthday();
    private Birthday() {
        super("Birthday",2);
    }
    /**
     * Will receive $ 2 M  from each player
     * @param state the current state of the game
     * @param performer the player who is performing the action
     */
    public void perform(State state, Player performer) {
    }
}
