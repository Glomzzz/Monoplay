package com.skillw.mono.card.action;

import com.skillw.mono.Color;
import com.skillw.mono.command.Command;
import com.skillw.mono.command.RentAllColor;
import com.skillw.mono.command.RentSingleColor;
import com.skillw.mono.game.Player;
import com.skillw.mono.game.GameState;

public class Rent extends ActionCard {

    public static final Rent BROWN_N_LIGHT_BLUE = new Rent("Rent Brown & Light Blue",Color.BROWN, Color.LIGHT_BLUE, 1);
    public static final Rent BLACK_N_LIGHT_GREEN = new Rent("Rent Black & Light Green",Color.BLACK, Color.LIGHT_GREEN, 1);
    public static final Rent BLUE_N_GREEN = new Rent("Rent Blue & Green",Color.BLUE, Color.GREEN, 1);
    public static final Rent PINK_N_ORANGE = new Rent("Rent Pink & Orange",Color.PINK, Color.ORANGE, 1);
    public static final Rent RED_N_YELLOW = new Rent("Rent Red & Yellow",Color.RED, Color.YELLOW, 1);

    private final Color color_1;
    private final Color color_2;

    private Rent(String name,Color color1, Color color2, int worth){
        super(name, worth);
        this.color_1 = color1;
        this.color_2 = color2;
    }


    /**
     * Rent for the properties, collect rent from the other players
     *
     * @param state     Game state
     * @param performer Player who performs the card
     * @return the command to be executed
     */
    public Command action(GameState state, Player performer){
        return new RentSingleColor(performer, color_1, color_2);
    }
}
