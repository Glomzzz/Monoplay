package com.skillw.mono.command;

import com.skillw.mono.game.Player;

public abstract class Command {
    public static int DRAW_CARDS = 0;
    public static int PAY_MONEY = 1;
    public static int ALL_PAY_MONEY = 2;
    public static int BUILD_PROPERTY = 3;
    public static int GIVE_PROPERTY = 4;
    public static int GIVE_COMPLETE_SET = 5;
    public static int RENT = 6;
    public static int RENT_UNIVERSAL = 7;


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
