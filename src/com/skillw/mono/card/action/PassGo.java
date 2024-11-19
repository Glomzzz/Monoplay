package com.skillw.mono.card.action;

import com.skillw.mono.card.PerformableCard;
import com.skillw.mono.command.Command;
import com.skillw.mono.command.DrawCards;
import com.skillw.mono.game.Player;

public class PassGo extends PerformableCard {

    public static final PassGo PASS_GO = new PassGo();

    //=============== Constructor =================
    //DEVELOPED BY: MORRO
    private PassGo() {
        super("Pass GO",1);
    }

    //DEVELOPED BY: MORRO
    /**
     * Draw 2 cards
     *
     * @param performer Player who performs the card
     * @return the command to be executed
     */
    public Command action(Player performer) {
        return new DrawCards(performer,2);
    }
}
