package com.skillw.mono.command;

import com.skillw.mono.game.Player;

public abstract class Command {
    public final static int DRAW_CARDS = 0;
    public final static int PAY_MONEY = 1;
    public final static int ALL_PAY_MONEY = 2;
    public final static int BUILD_PROPERTY = 3;
    public final static int GIVE_PROPERTY = 4;
    public final static int GIVE_COMPLETE_SET = 5;
    public final static int RENT = 6;
    public final static int RENT_UNIVERSAL = 7;
    public final static int DESPOIT_IN_BANK = 8;

    private final int id;
    private final Player performer;

    public Command(int id, Player performer) {
        this.id = id;
        this.performer = performer;
    }

    public int getId() {
        return id;
    }

    public Player getPerformer() {
        return performer;
    }
}
