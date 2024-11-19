package com.skillw.mono.command;

import com.skillw.mono.Color;
import com.skillw.mono.game.Player;

public class RentAllColor extends Command {

    //=============== Constructor =================
    //DEVELOPED BY: GLOM
    public RentAllColor(Player performer) {
        super(RENT_UNIVERSAL, performer);
    }

    //DEVELOPED BY: GLOM
    /**
     * Get the colors of the rent card
     *
     * @return colors of the rent card
     */
    public Color[] getColors() {
        return Color.UNIVERSAL;
    }
}
