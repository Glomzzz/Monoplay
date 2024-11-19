package com.skillw.mono.card.action;

import com.skillw.mono.card.PerformableCard;
import com.skillw.mono.command.AllPayMoney;
import com.skillw.mono.command.Command;
import com.skillw.mono.game.Player;


public class Birthday extends PerformableCard {

    public static final Birthday BIRTHDAY = new Birthday();

    //=============== Constructor =================
    //DEVELOPED BY: MORRO
    private Birthday() {
        super("Birthday",2);
    }

    //DEVELOPED BY: MORRO
    /**
     * Will collect $ 2 M  from each player
     *
     * @param performer the player who is performing the action
     * @return the command to be executed
     */
    public Command action(Player performer) {
       return new AllPayMoney(performer,5);
    }
}
