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
    private final int[] buildings;

    public PropertyList() {
        this.buildings = new int[10];
    }

    public void addBuilding(Color color){
        this.buildings[color.getId()] = this.buildings[color.getId()] + 1;
    }

    public int clearBuilding(Color color){
        int number = this.buildings[color.getId()];
        this.buildings[color.getId()] = 0;
        return number;
    }

    /**
     * Calculate the worth of the properties
     * @return the worth of the properties
     */
    public int calculateWorth(){
        int worth = 0;
        for (int i = 0; i < 10; i++) {
            worth +=  Color.UNIVERSAL[i].getLevels()[this.buildings[i]];
        }
        return worth;
    }
}
