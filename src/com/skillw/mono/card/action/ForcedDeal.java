package com.skillw.mono.card.action;

import com.skillw.mono.card.PerformableCard;
import com.skillw.mono.command.Command;
import com.skillw.mono.command.SwapProperty;
import com.skillw.mono.game.Player;

public class ForcedDeal extends PerformableCard {

    public static final ForcedDeal FORCED_DEAL = new ForcedDeal();

    //=============== Constructor =================
    //DEVELOPED BY: MORRO
    private ForcedDeal() {
        super("Forced Deal",3);
    }

    //DEVELOPED BY: MORRO
    /**
     * Swap any property with any player
     * Except the property is part of a full set
     *
     * @param performer Player who performs the card
     * @return the command to be executed
     */
    public Command action(Player performer) {
        return new SwapProperty(performer);
    }
}
