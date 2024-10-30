package com.skillw.mono.command;

import com.skillw.mono.card.Card;
import com.skillw.mono.game.Player;

public class DepositInBank extends Command{

    private final Card card;

    public DepositInBank(Player performer, Card card) {
        super(DEPOSIT_IN_BANK, performer);
        this.card = card;
    }

    public Card getCard() {
        return card;
    }
}