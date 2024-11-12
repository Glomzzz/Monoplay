package com.skillw.mono.card.action;

import com.skillw.mono.command.Command;
import com.skillw.mono.command.PayMoney;
import com.skillw.mono.game.Player;
import com.skillw.mono.game.GameState;

public class DebtCollector extends ActionCard {

    public static final DebtCollector DEBT_COLLECTOR = new DebtCollector();

    //=============== Constructor =================
    private DebtCollector() {
        super("Debt Collector",3);
    }

    /**
     * Ask any player for $ 5 M
     *
     * @param state     Current game state
     * @param performer Player who performs the card
     * @return the command to be executed
     */
    public Command action(GameState state, Player performer) {
        return new PayMoney(performer,5);
    }


}
