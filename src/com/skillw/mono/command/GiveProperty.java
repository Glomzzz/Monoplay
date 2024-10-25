package com.skillw.mono.command;

import com.skillw.mono.game.Player;

public class GiveProperty extends Command{

    public GiveProperty(Player performer) {
        super(GIVE_PROPERTY, performer);
    }
}
