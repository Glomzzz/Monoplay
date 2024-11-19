package com.skillw.mono.command;

import com.skillw.mono.game.Player;

public class PayMoney extends Command {
    private final int amount;

    //=============== Constructor =================
    //DEVELOPED BY: GLOM
    public PayMoney(Player performer,int amount) {
        super(PAY_MONEY, performer);
        this.amount = amount;
    }

    //DEVELOPED BY: GLOM
    /**
     * Get the amount of card to be paid
     *
     * @return the amount of card to be paid
     */
    public int getAmount() {
        return amount;
    }
}
