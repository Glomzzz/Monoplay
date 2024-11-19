package com.skillw.mono.game;


public class GameState {
    private int turn;
    private final Player[] players;
    private final CardStack cardStack;

    //=============== Constructor =================
    //DEVELOPED BY: GLOM
    public GameState(String[] players) {
        this.turn = 0;
        this.players = new Player[players.length];
        this.cardStack = new CardStack();
        for (int i = 0; i < players.length; i++) {
            this.players[i] = new Player(players[i], this.cardStack);
        }
    }

    //DEVELOPED BY: GLOM
    /**
     * Get all players
     *
     * @return an array with all the players
     */
    public Player[] getAllPlayers() {
        return players;
    }

    //DEVELOPED BY: GLOM
    /**
     * Initialize the game when the game first started
     */
    public void init(){
        cardStack.shuffle();
    }

    //DEVELOPED BY: MORRO
    /**
     * Get the cardStack
     *
     * @return the cardStack
     */
    public CardStack getCardStack() {
        return cardStack;
    }

    //DEVELOPED BY: MORRO
    /**
     * Get the current player on the game
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return this.players[this.turn % this.players.length];
    }

    //DEVELOPED BY: MORRO
    /**
     * Move onto the next turn
     */
    public void nextTurn() {
        this.turn++;
    }

    //DEVELOPED BY: MORRO
    /**
     * Check if there is a winner
     *
     * @return if there is a winner
     */
    public boolean hasWinner(){
        for (int i = 0; i < players.length; i++) {
            if(players[i].getPropertyList().getCompletedNum() >= 3){
                return true;
            }
        }
        return false;
    }

    //DEVELOPED BY: MORRO
    /**
     * Get the player who won
     *
     * @return the player
     */
    public Player getWinner(){
        for (int i = 0; i < players.length; i++) {
            if(players[i].getPropertyList().getCompletedNum() >= 3){
                return players[i];
            }
        }
        return null;
    }
}
