package com.skillw.mono.card.action;

import com.skillw.mono.command.Command;
import com.skillw.mono.command.TakeCompleteProperty;
import com.skillw.mono.game.Player;
import com.skillw.mono.game.GameState;

public class DealBreaker extends ActionCard {

    public static final DealBreaker DEAL_BREAKER = new DealBreaker();

    //=============== Constructor =================
    //DEVELOPED BY: MORRO
    private DealBreaker() {
        super("Deal Breaker",5);
    }

    //DEVELOPED BY: MORRO
    /**
     * Steal a complete property set from any player
     *
     * @param state     Current game state
     * @param performer Player who performs the card
     * @return the command to be executed
     */
    public Command action(GameState state, Player performer) {
        return new TakeCompleteProperty(performer);
    }
}
