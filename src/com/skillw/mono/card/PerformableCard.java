package com.skillw.mono.card;

import com.skillw.mono.game.Player;
import com.skillw.mono.game.State;

public abstract class PerformableCard extends Card {

    public PerformableCard(String name, int worth) {
        super(name, worth);
    }

    /**
     * Perform the card's action
     * @param state Game state
     * @param performer Player who performs the card
     */
    public abstract void perform(State state, Player performer);
}
