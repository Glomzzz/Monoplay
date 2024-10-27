package com.skillw.mono.game;

public class GameState {
    private int turn;
    private Player[] players;
    private CardStack cardStack;


    public GameState(String[] players) {
        this.turn = 0;
        this.players = new Player[players.length];
        for (int i = 0; i < players.length; i++) {
            this.players[i] = new Player(players[i]);
        }
    }

    public int getTurn() {
        return turn;
    }

    public Player[] getAllPlayers() {
        return players;
    }

    /**
     * Initialize the game when the game first started
     */
    public void init(){
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

    public Player getCurrentPlayer() {
        return this.players[this.turn % this.players.length];
    }
    /**
     * Every player has a turn, where they can play 3 cards at most
     */
    public void nextTurn() {
        this.turn++;
    }
}
