package com.skillw.mono.command;

import com.skillw.mono.Color;
import com.skillw.mono.game.Player;

public class RentSingleColor extends Command {
    private final Color[] colors;

    //=============== Constructor =================
    //DEVELOPED BY: GLOM
    public RentSingleColor(Player performer, Color[] colors) {
        super(RENT, performer);
        this.colors = colors;
    }

    //DEVELOPED BY: GLOM
    /**
     * Get the colors of the rent card
     *
     * @return colors
     */
    public Color[] getColors() {
        return colors;
    }
}
