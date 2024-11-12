package com.skillw.mono.card.action;

import com.skillw.mono.command.AllPayMoney;
import com.skillw.mono.command.Command;
import com.skillw.mono.game.Player;
import com.skillw.mono.game.GameState;


public class Birthday extends ActionCard {

    public static final Birthday BIRTHDAY = new Birthday();

    //=============== Constructor =================
    private Birthday() {
        super("Birthday",2);
    }
    /**
     * Will collect $ 2 M  from each player
     *
     * @param state     the current state of the game
     * @param performer the player who is performing the action
     * @return the command to be executed
     */
    public Command action(GameState state, Player performer) {
       return new AllPayMoney(performer,5);
    }
}
