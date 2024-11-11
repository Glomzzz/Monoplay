package com.skillw.mono.command;

import com.skillw.mono.card.Card;
import com.skillw.mono.card.Money;
import com.skillw.mono.game.Player;

public class DepositInBank extends Command{

    private final Money money;

    public DepositInBank(Player performer, Money card) {
        super(DEPOSIT_IN_BANK, performer);
        this.money = card;
    }

    public Card getMoney() {
        return money;
    }
}
