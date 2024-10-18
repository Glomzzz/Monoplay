package com.skillw.mono.card.action;

import com.skillw.mono.game.Player;
import com.skillw.mono.game.State;

public class DoubleTheRent extends ActionCard {
    public static final DoubleTheRent DOUBLE_THE_RENT = new DoubleTheRent();
    private DoubleTheRent() {
        super("Double The Rent",1);
    }

    /**
     * Double the rent of an rent action
     * @param state Game state
     * @param performer Player who performs the card
     */
    public void perform(State state, Player performer) {
    }
}
