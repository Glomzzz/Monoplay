package com.skillw.mono.card.action;

import com.skillw.mono.card.PerformableCard;
import com.skillw.mono.command.Command;
import com.skillw.mono.command.RentAllColor;
import com.skillw.mono.game.GameState;
import com.skillw.mono.game.Player;

public class RentUniversal extends PerformableCard {

    public static final RentUniversal RENT_UNIVERSAL = new RentUniversal();

    public RentUniversal() {
        super("Rent Universal", 3);
    }

    public Command perform(GameState state, Player performer) {
        return new RentAllColor(performer);
    }
}
