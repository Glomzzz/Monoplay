package com.skillw.mono.card.action;

import com.skillw.mono.command.Command;
import com.skillw.mono.command.TakeProperty;
import com.skillw.mono.game.Player;
import com.skillw.mono.game.GameState;

public class DealBreaker extends ActionCard {

    public static final DealBreaker DEAL_BREAKER = new DealBreaker();

    private DealBreaker() {
        super("Deal Breaker",5);
    }

    /**
     * Steal a complete set of properties from any player
     *
     * @param state     Game state
     * @param performer Player who performs the card
     * @return the command to be executed
     */
    public Command action(GameState state, Player performer) {
        return new TakeProperty(performer);
    }
}
