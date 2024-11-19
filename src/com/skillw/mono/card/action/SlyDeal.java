package com.skillw.mono.card.action;

import com.skillw.mono.card.PerformableCard;
import com.skillw.mono.command.Command;
import com.skillw.mono.command.TakeProperty;
import com.skillw.mono.game.Player;
import com.skillw.mono.game.GameState;

public class SlyDeal extends PerformableCard {

    public static final SlyDeal SLY_DEAL = new SlyDeal();

    //=============== Constructor =================
    //DEVELOPED BY: MORRO
    private SlyDeal() {
        super("SlyDeal", 3);
    }

    //DEVELOPED BY: MORRO
    /**
     * Steal a property from any player
     * But can't steal a property that is part of a complete set
     *
     * @param state     Current game state
     * @param performer Player who performs the card
     * @return the command to be executed
     */
    public Command action(GameState state, Player performer) {
        return new TakeProperty(performer);
    }
}
