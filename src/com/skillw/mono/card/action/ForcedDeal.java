package com.skillw.mono.card.action;

import com.skillw.mono.game.Player;
import com.skillw.mono.game.State;

public class ForcedDeal extends ActionCard {

    public static final ForcedDeal FORCED_DEAL = new ForcedDeal();

    private ForcedDeal() {
        super("Forced Deal",3);
    }

    /**
     * Swap any property with any player
     * @param state Game state
     * @param performer Player who performs the card
     */
    public void perform(State state, Player performer) {

    }
}
