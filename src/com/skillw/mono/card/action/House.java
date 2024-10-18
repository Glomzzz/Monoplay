package com.skillw.mono.card.action;

import com.skillw.mono.game.Player;
import com.skillw.mono.game.State;

public class House extends ActionCard {
    public static final House HOUSE = new House();
    private House() {
        super("House",3);
    }

    /**
     * Build a house on any property to increase rent $ 2 M
     * @param state Game state
     * @param performer Player who performs the card
     */
    public void perform(State state, Player performer) {

    }
}
