package com.skillw.mono.game;

public class GameState {
    private int turn;
    private Player[] players;
    private CardStack cardStack;

    public GameState(int turn, Player[] players) {
        this.turn = turn;
        this.players = players;
    }

    public int getTurn() {
        return turn;
    }

    public Player[] getAllPlayers() {
        return players;
    }

    public void start(){
        cardStack = new CardStack();
        cardStack.shuffle();
        /**
         * Draw 5 cards for each player at the beginning
         */
        for (Player player : this.players) {
            for (int i = 0; i < 5; i++) {
                player.getCardList().add(cardStack.draw());
            }
        }
    }

    public CardStack getCardStack() {
        return cardStack;
    }

    /**
     * Every player has a turn, where they can play 3 cards at most
     */
    public void nextTurn() {
        int current = this.turn++;
        if (current == this.players.length - 1) {
            this.turn = 0;
        }
    }

    public Player selectPlayer() {
        // todo
        return null;
    }
}
