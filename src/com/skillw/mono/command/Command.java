package com.skillw.mono.command;

import com.skillw.mono.game.Player;

public abstract class Command {

    public final static int DRAW_CARDS = 0;
    public final static int PAY_MONEY = 1;
    public final static int ALL_PAY_MONEY = 2;
    public final static int SET_PROPERTY = 3;
    public final static int SWAP_PROPERTY = 4;
    public final static int TAKE_PROPERTY = 5;
    public final static int TAKE_COMPLETE_PROPERTY = 6;
    public final static int RENT = 7;
    public final static int RENT_UNIVERSAL = 8;
    public final static int DEPOSIT_IN_BANK = 9;

    private final int id;
    private final Player performer;

    //=============== Constructor =================
    //DEVELOPED BY: GLOM
    public Command(int id, Player performer) {
        this.id = id;
        this.performer = performer;
    }

    //DEVELOPED BY: GLOM
    /**
     * Get the id of the command
     *
     * @return the id of the command
     */
    public int getId() {
        return id;
    }

    //DEVELOPED BY: GLOM
    /**
     * Get the player who perform the command
     *
     * @return the player who perform the command
     */
    public Player getPerformer() {
        return performer;
    }
}
