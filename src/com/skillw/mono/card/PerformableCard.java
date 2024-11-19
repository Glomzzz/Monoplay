package com.skillw.mono.card;

import com.skillw.mono.command.Command;
import com.skillw.mono.game.Player;
import com.skillw.mono.game.GameState;

public abstract class PerformableCard extends Card {

    //=============== Constructor =================
    //DEVELOPED BY: MORRO
    public PerformableCard(String name, int worth) {
        super(name, worth);
    }

    //DEVELOPED BY: MORRO
    /**
     * Perform the card's action
     *
     * @param state     Current game state
     * @param performer Player who performs the card
     * @return the command to be executed
     */
    public abstract Command action(GameState state, Player performer);
}
