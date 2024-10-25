package com.skillw.mono.command;

import com.skillw.mono.game.Player;

public class AllPayMoney extends Command {
    private final int amount;
    public AllPayMoney(Player performer, int amount) {
        super(ALL_PAY_MONEY, performer);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
