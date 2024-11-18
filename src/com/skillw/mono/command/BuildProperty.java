package com.skillw.mono.command;

import com.skillw.mono.card.Property;
import com.skillw.mono.game.Player;

public class BuildProperty extends Command {

    private final Property property;

    //=============== Constructor =================
    public BuildProperty(Player performer, Property property) {
        super(SET_PROPERTY, performer);
        this.property = property;
    }

    /**
     * Get the colors that the property contain
     *
     * @return the colors of the property
     */
    public Property getProperty() {
        return property;
    }
}
