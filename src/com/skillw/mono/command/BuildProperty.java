package com.skillw.mono.command;

import com.skillw.mono.Color;
import com.skillw.mono.game.Player;

public class BuildProperty extends Command {
    private final Color[] color;
    public BuildProperty(Player performer,Color[] color) {
        super(SET_PROPERTY, performer);
        this.color = color;
    }

    public Color[] getColor() {
        return color;
    }
}
