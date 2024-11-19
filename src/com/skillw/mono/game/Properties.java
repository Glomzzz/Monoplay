package com.skillw.mono.game;

import com.skillw.mono.Color;
import com.skillw.mono.card.Property;

public class Properties {
    private final Color color;
    private final Property[] data;  // Store the property cards
    private int size;

    //=============== Constructor =================
    //DEVELOPED BY: GLOM
    public Properties(Color color) {
        this.color = color;
        this.data = new Property[color.getMaxLevel()];
        this.size = 0;
    }

    //DEVELOPED BY: GLOM
    /**
     * Add a property card
     *
     * @param property     the property card
     * @return             if the card is successfully added
     */
    public boolean addProperty(Property property){
        if (isCompleted()) return false;
        this.data[this.size++] = property;
        return true;
    }

    //DEVELOPED BY: GLOM
    /**
     * Get the size
     *
     * @return  the size
     */
    public int getSize(){
        return this.size;
    }

    //DEVELOPED BY: GLOM
    /**
     * Get the properties
     *
     * @return  the properties
     */
    public Property[] getData() {
        return this.data;
    }

    //DEVELOPED BY: GLOM
    /**
     * Get the property
     *
     * @param index the property's index
     * @return      the property
     */
    public Property take(int index){
        Property property = this.data[index];
        this.data[index] = null;
        size--;
        // Shift the array, make sure there is no null in the middle
        for (int i = index; i < size; i++) {
            this.data[i] = this.data[i + 1];
        }
        this.data[size] = null;
        return property;
    }

    //DEVELOPED BY: GLOM
    /**
     * Get the color
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }


    //DEVELOPED BY: GLOM
    /**
     * Get the total worth
     *
     * @return the total worth
     */
    public int calculateTotalWorth(){
        if (size == 0)
            return 0;
        else
            return color.getLevels()[size-1];
    }

    //DEVELOPED BY: GLOM
    /**
     * Check if Properties is complete
     *
     * @return if it's complete
     */
    public boolean isCompleted(){
        return size == color.getMaxLevel();
    }

}
