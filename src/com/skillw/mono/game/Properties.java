package com.skillw.mono.game;

import com.skillw.mono.Color;
import com.skillw.mono.card.Property;

public class Properties {
    private final Color color;
    // Store the property card's colors
    private final Property[] data;
    private int size;

    public Properties(Color color) {
        this.color = color;
        this.data = new Property[color.getMaxLevel()];
        this.size = 0;
    }

    public boolean addProperty(Property property){
        if (isCompleted()) return false;
        this.data[this.size] = property;
        this.size++;
        return true;
    }

    public int getSize(){
        return this.size;
    }

    public Property[] getData() {
        return this.data;
    }

    public void takeAndSwapWith(int index, Property another){
        this.data[index] = another;
    }

    public Property take(int index){
        Property colors = this.data[index];
        this.data[index] = null;
        for (int i = index; i < size; i++) {
            this.data[i] = this.data[i + 1];
        }
        this.data[--size] = null;
        return colors;
    }

    public Color getColor() {
        return color;
    }


    public int calculateWorth(){
        if (size == 0)
            return 0;
        else
            return color.getLevels()[size-1];
    }

    public boolean isCompleted(){
        return size == color.getMaxLevel();
    }

}
