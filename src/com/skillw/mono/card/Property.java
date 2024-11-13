package com.skillw.mono.card;

import com.skillw.mono.Color;
import com.skillw.mono.command.BuildProperty;
import com.skillw.mono.command.Command;
import com.skillw.mono.game.Player;
import com.skillw.mono.game.GameState;

public class Property extends PerformableCard {

    public static final Property RED = new Property("Property Red",Color.RED);
    public static final Property BLUE = new Property("Property Blue",Color.BLUE);
    public static final Property PINK = new Property("Property Pink",Color.PINK);
    public static final Property GREEN = new Property("Property Green",Color.GREEN);
    public static final Property YELLOW = new Property("Property Yellow",Color.YELLOW);
    public static final Property BLACK = new Property("Property Black",Color.BLACK);
    public static final Property ORANGE = new Property("Property Orange",Color.ORANGE);
    public static final Property BROWN = new Property("Property Brown", Color.BROWN);
    public static final Property LIGHT_BLUE = new Property("Property Light Blue", Color.LIGHT_BLUE);
    public static final Property LIGHT_GREEN = new Property("Property Light Green", Color.LIGHT_GREEN);

    public static final Property BROWN_N_LIGHT_BLUE = new Property("Property Brown & Light",Color.BROWN, Color.LIGHT_BLUE, 1);
    public static final Property BLACK_N_LIGHT_GREEN = new Property("Property Black & Light Green",Color.BLACK, Color.LIGHT_GREEN, 2);
    public static final Property BLACK_N_GREEN = new Property("Property Black & Green",Color.BLACK, Color.GREEN, 4);
    public static final Property BLACK_N_LIGHT_BLUE = new Property("Property Black & Light Blue",Color.BLACK, Color.LIGHT_BLUE, 4);
    public static final Property BLUE_N_GREEN = new Property("Property Blue & Green",Color.BLUE, Color.GREEN, 4);
    public static final Property PINK_N_ORANGE = new Property("Property Pink & Orange",Color.PINK, Color.ORANGE, 2);
    public static final Property RED_N_YELLOW = new Property("Property Red & Yellow",Color.RED, Color.YELLOW, 3);

    public static final Property UNIVERSAL = new Property("Property Universal",Color.UNIVERSAL, 0);

    private final Color[] colors;
    //=============== Constructor =================
    private Property(String name,Color color) {
        super(name,color.getLevels()[0]);
        this.colors = new Color[] {color};
    }
    private Property(String name,Color color1, Color color2, int worth) {
        super(name,worth);
        this.colors = new Color[] {color1, color2};
    }
    private Property(String name,Color[] colors, int worth) {

        super(name,worth);
        this.colors = colors;
    }

    /**
     * Set the property to the player's property list
     *
     * @param state     Current game state
     * @param performer Player who performs the card
     * @return the command to be executed
     */
    public Command action(GameState state, Player performer) {
        return new BuildProperty(performer, colors);
    }

    public Color[] getColors() {
        return colors;
    }
}
