package com.skillw.mono.command;

import com.skillw.mono.Color;
import com.skillw.mono.game.Player;

public class RentSingleColor extends Command {
    private final Color[] colors;

    public RentSingleColor(Player performer, Color[] colors) {
        super(RENT, performer);
        this.colors = colors;
    }

    public Color[] getColors() {
        return colors;
    }
}
