package com.skillw.mono.command;

import com.skillw.mono.Color;
import com.skillw.mono.game.Player;

public class BuildProperty extends Command {
    private final Color[] domain;
    public BuildProperty(Player performer,Color[] domain) {
        super(BUILD_PROPERTY, performer);
        this.domain = domain;
    }

    public Color[] getDomain() {
        return domain;
    }
}
