package com.skillw.mono.card;

import com.skillw.mono.command.Command;
import com.skillw.mono.command.DepositInBank;
import com.skillw.mono.game.Player;
import com.skillw.mono.game.GameState;

public class Money extends PerformableCard {

    public static final Money ONE = new Money(1);
    public static final Money TWO = new Money(2);
    public static final Money THREE = new Money(3);
    public static final Money FOUR = new Money(4);
    public static final Money FIVE = new Money(5);
    public static final Money TEN = new Money(10);

    //=============== Constructor =================
    //DEVELOPED BY: MORRO
    public Money(int worth) {
        super("$ " + worth + "M", worth);
    }

    //DEVELOPED BY: MORRO
    /**
     * Store the money in the player's bank
     *
     * @param state     Current game state
     * @param performer Player who performs the card
     * @return the command to be executed
     */
    public Command action(GameState state, Player performer) {
        return new DepositInBank(performer, this);
    }
}
