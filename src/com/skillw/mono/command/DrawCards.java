package com.skillw.mono.command;

import com.skillw.mono.game.Player;

public class DrawCards extends Command{

    public DrawCards(Player performer) {
        super(DRAW_CARDS, performer);
    }
}
