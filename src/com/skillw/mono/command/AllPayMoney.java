package com.skillw.mono.command;

import com.skillw.mono.game.Player;

public class AllPayMoney extends Command {

    private final int amount;

    //=============== Constructor =================
    //DEVELOPED BY: GLOM
    public AllPayMoney(Player performer, int amount) {
        super(ALL_PAY_MONEY, performer);
        this.amount = amount;
    }

    //DEVELOPED BY: GLOM
    /**
     * Get the amount players have to pay
     *
     * @return amount to be paid
     */
    public int getAmount() {
        return amount;
    }
}
