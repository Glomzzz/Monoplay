package com.skillw.mono.command;

import com.skillw.mono.card.Card;
import com.skillw.mono.game.Player;

public class DespoitInBank extends Command{

    private final Card card;

    public DespoitInBank(Player performer,Card card) {
        super(DESPOIT_IN_BANK, performer);
        this.card = card;
    }

    public Card getCard() {
        return card;
    }
}
