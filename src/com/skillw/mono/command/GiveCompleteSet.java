package com.skillw.mono.command;

import com.skillw.mono.game.Player;

public class GiveCompleteSet extends Command{

    public GiveCompleteSet(Player performer) {
        super(GIVE_COMPLETE_SET, performer);
    }
}
