package com.skillw.mono.card.action;

import com.skillw.mono.card.PerformableCard;
import com.skillw.mono.command.Command;
import com.skillw.mono.command.TakeCompleteProperty;
import com.skillw.mono.game.Player;

public class DealBreaker extends PerformableCard {

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
     * @param performer Player who performs the card
     * @return the command to be executed
     */
    public Command action(Player performer) {
        return new TakeCompleteProperty(performer);
    }
}
