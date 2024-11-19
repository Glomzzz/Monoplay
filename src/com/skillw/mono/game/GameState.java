package com.skillw.mono.game;


public class GameState {
    private int turn;
    private Player[] players;
    private CardStack cardStack;


    public GameState(String[] players) {
        this.turn = 0;
        this.players = new Player[players.length];
        this.cardStack = new CardStack();
        for (int i = 0; i < players.length; i++) {
            this.players[i] = new Player(players[i], this.cardStack);
        }
    }

    public Player[] getAllPlayers() {
        return players;
    }


    /**
     * Initialize the game when the game first started
     */
    public void init(){
        cardStack.shuffle();
        /*
         * Draw 3 cards for each player at the beginning
         * (+2 will be added when it's their turn)
         */
        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < 3; j++) {
                players[i].getCardList().add(cardStack.draw());
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
     * Every player has a turn, where they can do 3 actions at most
     */
    public void nextTurn() {
        this.turn++;
    }

    public boolean hasWinner(){
        for (int i = 0; i < players.length; i++) {
            if(players[i].getPropertyList().getCompletedNum() >= 3){
                return true;
            }
        }
        return false;
    }
    public Player getWinner(){
        for (int i = 0; i < players.length; i++) {
            if(players[i].getPropertyList().getCompletedNum() >= 3){
                return players[i];
            }
        }
        return null;
    }
}
