package com.skillw.mono.game;

import com.skillw.mono.Color;
import com.skillw.mono.card.Property;

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
    private final Properties[] properties;

    public PropertyList() {
        this.properties = new Properties[]{
            new Properties(Color.RED),
            new Properties(Color.BLUE),
            new Properties(Color.GREEN),
            new Properties(Color.YELLOW),
            new Properties(Color.BLACK),
            new Properties(Color.ORANGE),
            new Properties(Color.PINK),
            new Properties(Color.LIGHT_BLUE),
            new Properties(Color.BROWN),
            new Properties(Color.LIGHT_GREEN)
        };
    }

    public boolean addProperty(Property property, Color color){
       return this.properties[color.getId()].addProperty(property);
    }

    public Properties getProperties(Color color){
        return this.properties[color.getId()];
    }

    /**
     * Calculate the worth of all the properties
     * @return the worth of the properties
     */
    public int calculateWorthOfAll(){
        int worth = 0;
        for (int i = 0; i < 10; i++) {
            int number = this.properties[i].getSize();
            if (number != 0)
                worth +=  Color.UNIVERSAL[i].getLevels()[number];
        }
        return worth;
    }

    public int getCompleteNum() {
        int count = 0;
        Properties[] properties1 = this.properties;
        for (int i = 0; i < properties1.length; i++) {
            Properties properties = properties1[i];
            if (properties.isCompleted()) count++;
        }
        return count;
    }
}
