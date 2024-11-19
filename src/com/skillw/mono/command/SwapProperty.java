package com.skillw.mono.command;

import com.skillw.mono.game.Player;

public class SwapProperty extends Command{

    //=============== Constructor =================
    public SwapProperty(Player performer) {
        super(SWAP_PROPERTY, performer);
    }
}
