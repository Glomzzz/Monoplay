package com.skillw.mono.card.action;

import com.skillw.mono.command.Command;
import com.skillw.mono.command.SwapProperty;
import com.skillw.mono.game.Player;
import com.skillw.mono.game.GameState;

public class ForcedDeal extends ActionCard {

    public static final ForcedDeal FORCED_DEAL = new ForcedDeal();

    //=============== Constructor =================
    private ForcedDeal() {
        super("Forced Deal",3);
    }

    /**
     * Swap any property with any player
     *
     * @param state     Current game state
     * @param performer Player who performs the card
     * @return the command to be executed
     */
    public Command action(GameState state, Player performer) {
        return new SwapProperty(performer);
    }
}
