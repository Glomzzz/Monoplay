package com.skillw.mono.command;

import com.skillw.mono.game.Player;

public class TakeCompleteProperty extends Command{

    //=============== Constructor =================
    public TakeCompleteProperty(Player performer) {
        super(TAKE_COMPLETE_PROPERTY, performer);
    }
}
