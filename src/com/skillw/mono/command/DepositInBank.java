package com.skillw.mono.command;

import com.skillw.mono.card.Card;
import com.skillw.mono.card.Money;
import com.skillw.mono.game.Player;

public class DepositInBank extends Command{

    private final Money money;

    //=============== Constructor =================
    //DEVELOPED BY: GLOM
    public DepositInBank(Player performer, Money card) {
        super(DEPOSIT_IN_BANK, performer);
        this.money = card;
    }

    //DEVELOPED BY: GLOM
    /**
     * Get the money card
     *
     * @return the money card
     */
    public Money getMoney() {
        return money;
    }
}
