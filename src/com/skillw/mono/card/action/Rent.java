package com.skillw.mono.card.action;

import com.skillw.mono.Color;
import com.skillw.mono.game.Player;
import com.skillw.mono.game.State;

public class Rent extends ActionCard {
    
    public static final Rent UNIVERSAL = new Rent("Rent Universal",Color.UNIVERSAL, 3);

    public static final Rent BROWN_N_LIGHT_BLUE = new Rent("Rent Brown & Light Blue",Color.BROWN, Color.LIGHT_BLUE, 1);
    public static final Rent BLACK_N_LIGHT_GREEN = new Rent("Rent Black & Light Green",Color.BLACK, Color.LIGHT_GREEN, 1);
    public static final Rent BLUE_N_GREEN = new Rent("Rent Blue & Green",Color.BLUE, Color.GREEN, 1);
    public static final Rent PINK_N_ORANGE = new Rent("Rent Pink & Orange",Color.PINK, Color.ORANGE, 1);
    public static final Rent RED_N_YELLOW = new Rent("Rent Red & Yellow",Color.RED, Color.YELLOW, 1);

    private final Color[] colors;

    private Rent(String name,Color color1, Color color2, int worth){
        this(name, new Color[] {color1, color2}, worth);
    }

    private Rent(String name,Color[] colors, int worth){
        super(name,worth);
        this.colors = colors;
    }

    /**
     * Rent for the properties, collect rent from the other players
     * @param state Game state
     * @param performer Player who performs the card
     */
    public void perform(State state, Player performer){

    }
}
