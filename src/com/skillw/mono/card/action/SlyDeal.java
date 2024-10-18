package com.skillw.mono.card.action;

import com.skillw.mono.game.Player;
import com.skillw.mono.game.State;

public class SlyDeal extends ActionCard {
    public static final SlyDeal SLY_DEAL = new SlyDeal();

    private SlyDeal() {
        super("SlyDeal", 3);
    }

    public void perform(State state, Player performer) {

    }
}
