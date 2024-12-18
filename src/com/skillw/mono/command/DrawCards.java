package com.skillw.mono.command;

import com.skillw.mono.game.Player;

public class DrawCards extends Command{

    private final int amount;

    //=============== Constructor =================
    public DrawCards(Player performer,int amount) {
        super(DRAW_CARDS, performer);
        this.amount = amount;
    }

    //DEVELOPED BY: GLOM
    /**
     * Get the amount of card to be drawn
     *
     * @return the amount of card to be drawn
     */
    public int getAmount() {
        return amount;
    }
}
