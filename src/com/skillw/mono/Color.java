package com.skillw.mono;

public class Color {

    public static final Color RED = new Color(0,"Red", new int[]{2, 3, 6} );
    public static final Color BLUE = new Color(1,"Blue", new int[]{3,8});
    public static final Color GREEN = new Color(2,"Green", new int[]{2,4,7});
    public static final Color YELLOW = new Color(3,"Yellow", new int[]{2,4,6});
    public static final Color BLACK = new Color(4,"Black", new int[]{1,2,3,4});
    public static final Color ORANGE = new Color(5,"Orange", new int[]{1,3,5});
    public static final Color PINK = new Color(6,"Pink", new int[]{1,2,4});
    public static final Color LIGHT_BLUE = new Color(7,"Light Blue", new int[]{1,2,3});
    public static final Color BROWN = new Color(8,"Brown", new int[]{1,2});
    public static final Color LIGHT_GREEN = new Color(9,"Light Green", new int[]{1,2});

    /* Store all colors
     * The position of the colors in the array
     * will reflect the ID of the color*/
    public static final Color[] UNIVERSAL = new Color[]{RED, BLUE, GREEN, YELLOW, BLACK, ORANGE, PINK, LIGHT_BLUE, BROWN, LIGHT_GREEN};

    private final int id;
    private final String name;
    /* Store the rent level, based on the number of color.
     * For example, if a player owns 2 Red Property and 1 Blue Property
     * He can receive the rent of 3 + 3 = 6 M */
    private final int[] levels;

    private Color(int id,String name, int[] levels) {
        this.id = id;
        this.name = name;
        this.levels = levels;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int[] getLevels() {
        return levels;
    }

    public int getMaxLevel() {
        return levels.length;
    }
}
