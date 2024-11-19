package com.skillw.mono.command;

import com.skillw.mono.Color;
import com.skillw.mono.game.Player;

public class RentAllColor extends Command {

    //=============== Constructor =================
    public RentAllColor(Player performer) {
        super(RENT_UNIVERSAL, performer);
    }

    /**
     * Get the colors of the rent card
     *
     * @return colors
     */
    public Color[] getColors() {
        return Color.UNIVERSAL;
    }
}
