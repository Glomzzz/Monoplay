package com.skillw.mono.command;

import com.skillw.mono.game.Player;

public class TakeProperty extends Command{

    public TakeProperty(Player performer) {
        super(TAKE_PROPERTY, performer);
    }
}
