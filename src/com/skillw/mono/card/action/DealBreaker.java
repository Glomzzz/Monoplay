package com.skillw.mono.card.action;

import com.skillw.mono.game.Player;
import com.skillw.mono.game.State;

public class DealBreaker extends ActionCard {

    public static final DealBreaker DEAL_BREAKER = new DealBreaker();

    private DealBreaker() {
        super("Deal Breaker",5);
    }

    /**
     * Steal a complete set of properties from any player
     * @param state Game state
     * @param performer Player who performs the card
     */
    public void perform(State state, Player performer) {
        Player target = state.selectPlayer();
    }
}
