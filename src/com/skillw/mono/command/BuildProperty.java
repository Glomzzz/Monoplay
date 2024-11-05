package com.skillw.mono.command;

import com.skillw.mono.Color;
import com.skillw.mono.game.Player;

public class BuildProperty extends Command {
    private final Color[] colors;
    public BuildProperty(Player performer,Color[] colors) {
        super(SET_PROPERTY, performer);
        this.colors = colors;
    }

    public Color[] getColors() {
        return colors;
    }
}
