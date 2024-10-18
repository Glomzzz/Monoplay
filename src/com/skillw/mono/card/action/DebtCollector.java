package com.skillw.mono.card.action;

import com.skillw.mono.game.Player;
import com.skillw.mono.game.State;

public class DebtCollector extends ActionCard {

    public static final DebtCollector DEBT_COLLECTOR = new DebtCollector();

    private DebtCollector() {
        super("Debt Collector",3);
    }

    /**
     * Ask any player for $ 5 M
     * @param state Game state
     * @param performer Player who performs the card
     */
    public void perform(State state, Player performer) {
    }


}
