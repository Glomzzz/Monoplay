package com.skillw.mono.game;

import com.skillw.mono.Color;

/**
 * This class represents the list of properties that a player has.
 */
public class PropertyList {
    /**
     * - 0 : Red
     * - 1 : Blue
     * - 2 : Green
     * - 3 : Yellow
     * - 4 : Black
     * - 5 : Orange
     * - 6 : Pink
     * - 7 : Light Blue
     * - 8 : Brown
     * - 9 : Light Green
     */
    private final int[] properties;

    public PropertyList() {
        this.properties = new int[10];
    }

    public void addProperty(Color color){
        this.properties[color.getId()] = this.properties[color.getId()] + 1;
    }

    public int clearProperties(Color color){
        int number = this.properties[color.getId()];
        this.properties[color.getId()] = 0;
        return number;
    }

    public int calculateWorthOf(Color color){
        int number = this.properties[color.getId()];
        if (number == 0)
            return 0;
        else
            return color.getLevels()[number];
    }

    /**
     * Calculate the worth of all the properties
     * @return the worth of the properties
     */
    public int calculateWorthOfAll(){
        int worth = 0;
        for (int i = 0; i < 10; i++) {
            int number = this.properties[i];
            if (number != 0)
                worth +=  Color.UNIVERSAL[i].getLevels()[number];
        }
        return worth;
    }
}
