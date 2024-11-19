package com.skillw.mono.card.action;

import com.skillw.mono.Color;
import com.skillw.mono.card.PerformableCard;
import com.skillw.mono.command.Command;
import com.skillw.mono.command.RentSingleColor;
import com.skillw.mono.game.Player;
import com.skillw.mono.game.GameState;

public class Rent extends PerformableCard {

    public static final Rent BROWN_N_LIGHT_BLUE = new Rent("Rent Brown & Light Blue",Color.BROWN, Color.LIGHT_BLUE, 1);
    public static final Rent BLACK_N_LIGHT_GREEN = new Rent("Rent Black & Light Green",Color.BLACK, Color.LIGHT_GREEN, 1);
    public static final Rent BLUE_N_GREEN = new Rent("Rent Blue & Green",Color.BLUE, Color.GREEN, 1);
    public static final Rent PINK_N_ORANGE = new Rent("Rent Pink & Orange",Color.PINK, Color.ORANGE, 1);
    public static final Rent RED_N_YELLOW = new Rent("Rent Red & Yellow",Color.RED, Color.YELLOW, 1);

    private final Color[] colors;

    //=============== Constructor =================
    //DEVELOPED BY: MORRO
    private Rent(String name,Color color1, Color color2, int worth){
        super(name, worth);
        this.colors = new Color[]{color1, color2};
    }

    //DEVELOPED BY: MORRO
    /**
     * Rent for the properties, collect rent from the other players
     *
     * @param state     Current game state
     * @param performer Player who performs the card
     * @return the command to be executed
     */
    public Command action(GameState state, Player performer){
        return new RentSingleColor(performer, colors);
    }
}
