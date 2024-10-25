package com.skillw.mono.command;

import com.skillw.mono.game.Player;

public class RentAllColor extends Command {
    public RentAllColor(Player performer) {
        super(RENT_UNIVERSAL, performer);
    }

}
