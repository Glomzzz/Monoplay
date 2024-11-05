package com.skillw.mono.card.action;

import com.skillw.mono.command.Command;
import com.skillw.mono.command.DrawCards;
import com.skillw.mono.game.Player;
import com.skillw.mono.game.GameState;

public class PassGo extends ActionCard {
    public static final PassGo PASS_GO = new PassGo();
    private PassGo() {
        super("Pass GO",1);
    }

    /**
     * Pass GO and draw 2 cards
     *
     * @param state     Game state
     * @param performer Player who performs the card
     * @return the command to be executed
     */
    public Command action(GameState state, Player performer) {
        return new DrawCards(performer,2);
    }
}
