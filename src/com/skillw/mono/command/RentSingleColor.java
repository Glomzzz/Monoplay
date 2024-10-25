package com.skillw.mono.command;

import com.skillw.mono.Color;
import com.skillw.mono.game.Player;

public class RentSingleColor extends Command {
    private final Color color1;
    private final Color color2;

    public RentSingleColor(Player performer, Color color1, Color color2) {
        super(RENT, performer);
        this.color1 = color1;
        this.color2 = color2;
    }

    public Color getColor1() {
        return color1;
    }

    public Color getColor2() {
        return color2;
    }

}
