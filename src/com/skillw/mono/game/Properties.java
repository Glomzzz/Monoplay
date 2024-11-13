package com.skillw.mono.game;

import com.skillw.mono.Color;

public class Properties {
    private final Color color;
    // Store the property card's colors
    private final Color[][] data;
    private int size;

    public Properties(Color color) {
        this.color = color;
        this.data = new Color[color.getMaxLevel()][2];
        this.size = 0;
    }

    public void addProperty(Color[] colors){
        this.data[this.size] = colors;
        this.size++;
    }

    public int getSize(){
        return this.size;
    }

    public Color[][] getData() {
        return this.data;
    }

    public void takeAndSwapWith(int index, Color[] colors){
        this.data[index] = colors;
    }

    public Color[] take(int index){
        Color[] colors = this.data[index];
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

}
