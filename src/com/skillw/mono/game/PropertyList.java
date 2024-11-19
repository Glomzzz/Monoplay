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

    //=============== Constructor =================
    //DEVELOPED BY: MORRO
    //The position of the properties reflect the color's ID
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

    //DEVELOPED BY: MORRO
    /**
     * Add a property based on the chosen color to add to Properties
     *
     * @param property the property to be added
     * @param color the chosen color
     * @return if the property is successfully added
     */
    public boolean addProperty(Property property, Color color){
       return this.properties[color.getId()].addProperty(property);
    }

    //DEVELOPED BY: MORRO
    /**
     * Get Properties
     * @param color the color of Properties
     * @return Properties
     */
    public Properties getProperties(Color color){
        return this.properties[color.getId()];
    }

    //DEVELOPED BY: MORRO
    /**
     * Calculate the worth of all the properties
     *
     * @return the worth of the properties
     */
    public int calculateWorthOfAll(){
        int worth = 0;
        for (int i = 0; i < 10; i++) {
            int number = this.properties[i].getSize();
            if (number != 0)
                worth +=  Color.UNIVERSAL[i].getLevels()[number-1];
        }
        return worth;
    }

    //DEVELOPED BY: MORRO
    /**
     * Get the number of commpleted Properties
     * @return the number of commpleted Properties
     */
    public int getCompletedNum() {
        int count = 0;
        for (int i = 0; i < this.properties.length; i++) {
            Properties property = this.properties[i];
            if (property.isCompleted()) count++;
        }
        return count;
    }

    // DEVELOPED BY: MORRO
    /**
     * Clear the color of the properties
     * @param color the color to be cleared
     */
    public void clearColor(Color color) {
        this.properties[color.getId()] = new Properties(color);
    }
}
