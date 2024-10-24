package com.skillw.mono.game;

public class Player {
    private final String name;
    private final Bank bank;
    private final CardList cardList;
    private final PropertyList propertyList;

    public Player(String name) {
        this.name = name;
        this.bank = new Bank();
        this.cardList = new CardList();
        this.propertyList = new PropertyList();
    }

    public String getName() {
        return name;
    }

    public Bank getBank() {
        return bank;
    }

    public CardList getCardList() {
        return cardList;
    }

    public PropertyList getPropertyList() {
        return propertyList;
    }

    /**
     * Pay the player at least the specified amount.
     * @param player the player to pay
     * @param atLeast the amount to pay
     */
    public void pay(Player player, int atLeast) {
        //todo
    }

}
