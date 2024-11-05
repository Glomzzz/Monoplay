package com.skillw.mono.command;

import com.skillw.mono.game.Player;

public class DrawCards extends Command{

    private int amount;

    public DrawCards(Player performer,int amount) {
        super(DRAW_CARDS, performer);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
