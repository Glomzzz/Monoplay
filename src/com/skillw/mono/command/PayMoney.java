package com.skillw.mono.command;

import com.skillw.mono.game.Player;

public class PayMoney extends Command {
    private final int amount;
    public PayMoney(Player performer) {
        super(PAY_MONEY, performer);
        this.amount = 0;
    }
    public PayMoney(Player performer,int amount) {
        super(PAY_MONEY, performer);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
