package com.skillw.mono.card.action;

import com.skillw.mono.card.PerformableCard;
import com.skillw.mono.command.Command;
import com.skillw.mono.command.RentAllColor;
import com.skillw.mono.game.Player;

public class RentUniversal extends PerformableCard {

    public static final RentUniversal RENT_UNIVERSAL = new RentUniversal();

    //=============== Constructor =================
    //DEVELOPED BY: MORRO
    public RentUniversal() {
        super("Rent Universal", 3);
    }

    //DEVELOPED BY: MORRO
    /**
     * Rent for the properties, collect rent from the other players
     *
     * @param performer Player who performs the card
     * @return the command to be executed
     */
    public Command action(Player performer) {
        return new RentAllColor(performer);
    }
}
