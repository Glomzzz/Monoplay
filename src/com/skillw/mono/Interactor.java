package com.skillw.mono;

import com.skillw.mono.card.Card;
import com.skillw.mono.card.Money;
import com.skillw.mono.card.PerformableCard;
import com.skillw.mono.card.Property;
import com.skillw.mono.card.special.DoubleTheRent;
import com.skillw.mono.card.special.No;
import com.skillw.mono.game.*;
import com.skillw.mono.util.CardCounter;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Interactor {

    private static final String PREFIX = "%-9s>>    ";
    private static final String CARD_FORMAT = PREFIX + "%d. %-25s%n";

    private static final String ITEM_FORMAT = PREFIX + "%d. %-25s    x%-2d    ~ $ %dM %n";
    private static final String PROPERTY_HEAD = PREFIX + "    Properties: %n";
    private static final String PROPERTY_FORMAT = PREFIX + "    |-  %d. %-11s    ( %d / %d )    - $ %dM %n";
    private static final String PROPERTY_NO_INDEX_FORMAT = PREFIX + "    |-  %-11s    ( %d / %d )    - $ %dM %n";
    private static final String PROPERTIES_FORMAT = PREFIX + "        |-  %d. %s %n";
    private static final String PROPERTIES_NO_INDEX_FORMAT = PREFIX + "        |-    %s %n";
    private static final String PROPERTY_EMPTY = PREFIX + "    |-  No properties owned! %n";
    private static final String BANK_HEAD = PREFIX + "    Bank ( $ %d M ): %n";
    private static final String MONEY_FORMAT = PREFIX + "        |-  %-11s    x%-2d %n";
    private static final String BANK_EMPTY = PREFIX + "    |-  No money in bank! %n";
    private static final String COLOR_LEVEL_FORMAT = PREFIX + "%d. %10s   ( %d / %d )   =>   ( %d / %d )";
    private static final String COLOR_MONEY_FORMAT = PREFIX + "%d. %10s   ( $%d M )";
    private static final String MESSAGE_FORMAT = PREFIX + "%s";

    public static final byte ALL = 0;
    public static final byte COMPLETED = 1;
    public static final byte INCOMPLETE = 2;

    public final static byte COLOR_FILTER_ALL = 0;
    public final static byte COLOR_FILTER_OWNED = 1;
    public final static byte COLOR_FILTER_INCOMPLETE = 2;

    private static final int MAX_PLAYER = 5;

    private final Scanner input;
    private GameState game = null;
    /**
     * The player that determines the prefix of the message
     * <p>
     * You'll see the code like this:
     *         Player original = prefixPlayer;
     *         prefixPlayer = player
     *         ...
     *         prefixPlayer = original;
     * <p>
     * This is to ensure that the prefixPlayer is not changed after the method is called
     *
     */
    private Player prefixPlayer = null;

    //DEVELOPED BY: MORRO
    public Interactor(){
        this.input = new Scanner(System.in);
    }

    //DEVELOPED BY: GLOM
    /**
     * Initialize the game
     *
     * @param game the game
     */
    public void init(GameState game){
        this.game = game;
    }

    //DEVELOPED BY: GLOM
    /**
     * Close the input stream
     */
    public void exit(){
        input.close();
    }

    //DEVELOPED BY: MORRO
    /**
     * Display the game title
     */
    public void displayHeader(){
        System.out.println("======================================");
        System.out.println("|         Welcome to Monopoly        |");
        System.out.println("======================================");
    }

    //DEVELOPED BY: MORRO
    /**
     * Generate a huge empty gap
     */
    public void emptyGap(){
        for(int i = 0; i < 30; i ++){
            System.out.println();
        }
    }

    //DEVELOPED BY: GLOM
    /**
     * Display the game board ( each player's state, including properties and bank)
     */
    public void displayState(){
        Player[] players = game.getAllPlayers();
        for (int i=0; i<players.length; i++){
            Player player = players[i];
            println(player.getName() + ":");
            displayPropertyList(player.getPropertyList());
            displayBank(player.getBank());
            println("");
            waitForPlayer();
        }
    }

    //DEVELOPED BY: MORRO
    /**
     * Set the current player, which determines the prefix of the message
     *
     * @param player the player to be set as current player
     */
    public void setPrefixPlayer(Player player) {
        this.prefixPlayer = player;
    }

    //DEVELOPED BY: MORRO
    /**
     * Money format
     *
     * @param amount the amount of money
     *
     * @return the formatted money
     */
    public String moneyFormat(int amount){
        return "$ " + amount + "M";
    }

    //DEVELOPED BY: MORRO
    /**
     * Read an integer from the input stream
     * Use recursion to handle invalid input
     *
     * @param prompt the message to be displayed
     * @return the integer read from the input stream
     */
    public int readInt(String prompt){
        try {
            print(prompt);
            int result = input.nextInt();
            // Consume the newline character
            input.nextLine();
            return result;
        } catch (InputMismatchException e){
            println("Invalid input, please enter a number!");
            // Consume the newline character
            input.nextLine();
            // Recursion
            return readInt(prompt);
        }
    }

    //DEVELOPED BY: MORRO
    /**
     * Register the players in the game
     *
     * @return   - the array of players
     */
    public String[] registerPlayers(){
        System.out.println("Please enter players name, enter 'done' to finish.");
        // Initialize the array of players
        // The maximum number of players is 5
        String[] names = new String[MAX_PLAYER];
        int count = 0;
        while (count < MAX_PLAYER){
            System.out.print("Player " + (count+1) + ": ");
            String name = input.next();
            // Consume the newline character
            input.nextLine();
            if (name.equals("done")){
                if (count < 2){
                    System.out.println("At least 2 players are needed to start the game.");
                } else {
                    System.out.println("All players have been registered.");
                    break;
                }
            } else for (int i = 0; i < names.length; i++) {
                // If the name is not registered
                if (names[i] == null){
                    // Register the name
                    names[i] = name;
                    count++;
                    System.out.println("Player " + name + " has been registered.");
                    break;
                }
            }
        }
        // Ensure non-null values in the array
        String[] result = new String[count];
        for (int i = 0; i < count; i++) {
            result[i] = names[i];
        }
        return result;
    }

    //DEVELOPED BY: MORRO
    /**
     * Display the number of special cards
     *
     * @param player the player to be displayed
     */
    public void displaySpecialCardNum(Player player){
        println("Double The Rent (" + player.getCardList().getNumOfDoubleTheRent() + ")" + "  Just Say No (" + player.getCardList().getNumOfNo() + ")");
    }

    //DEVELOPED BY: GLOM
    /**
     * Display the number of special cards
     *
     * @param player the player to be displayed
     * @param card the card to be displayed
     * @param from the start index
     */
    private void recieve(Player player, Card card, Player from){
        Player temp = prefixPlayer;
        prefixPlayer = player;
        alert("You get a " + card.getName() + " from " + from.getName());
        // If the card is a property
        if (card instanceof Property){
            // Ask the player to set the property
            setProperty(player,(Property) card);
        } else {
            player.recieveCard(card);
        }
        prefixPlayer = temp;
    }

    //DEVELOPED BY: MORRO
    /**
     * Ask a player to pay another player
     *
     * @param from the player who will pay
     * @param to the player who will receive the payment
     * @param amount the amount of money to be paid
     */
    public void askToPay(Player from, Player to, int amount){
        Bank bank = from.getBank();
        PropertyList propertyList = from.getPropertyList();
        CardList cardList = from.getCardList();

        int bankWorth = bank.calculateTotalWorth();
        int propertyWorth = propertyList.calculateWorthOfAll();
        int cardWorth = cardList.calculateTotalWorth();

        // If the player don't have enough money
        if (bankWorth + cardWorth + propertyWorth < amount){
            // Take all the cards, properties, money from the player
            println(from.getName() + " don't have enough money & cards to pay, so you will take all cards from " + from.getName());
            for (int i = 0; i < CardList.CARDS.length; i++) {
                Card card = CardList.CARDS[i];
                int num = cardList.clearOf(i);
                for (int j = 0; j < num; j++) {
                    recieve(to,card,from);
                }
            }
            for (int i = 0; i < Bank.MONEYS.length; i++) {
                Money money = Bank.MONEYS[i];
                int num = bank.clearOf(money);
                for (int j = 0; j < num; j++) {
                    recieve(to,money,from);
                }
            }
            for (int i = 0; i < Color.UNIVERSAL.length; i++) {
                Properties properties = propertyList.getProperties(Color.UNIVERSAL[i]);
                for (int j = 0; j < properties.getSize(); j++) {
                    Property property = properties.take(j);
                    recieve(to,property,from);
                }
            }
        } else {
            prefixPlayer = from;
            // Pay the money
            println(from.getName() + " , you have to pay " + moneyFormat(amount) + " to " + to.getName());
            int paid = 0;
            while (paid < amount){
                // take money from the bank first
                if (bankWorth > 0){
                    println("You have " + moneyFormat(bankWorth) + " in bank, you have to pay with money in bank first.");
                    println("How much money will you pay with money in bank? *stare*  ( " + moneyFormat(amount - paid) + " left )");
                    Money money  = (Money) selectCardInBank(from,false);
                    bank.take(money);
                    recieve(to,money,from);
                    paid += money.getWorth();
                    bankWorth = bank.calculateTotalWorth();

                // if the bank is insufficient, the game will ask from property
                } else if (propertyWorth > 0){
                    println("You have " + moneyFormat(propertyWorth) + " in properties, you have to pay with properties.");
                    println("Which property will you pay with? Choose wisely *wink*  ( " + moneyFormat(amount - paid) + " left )");
                    Property property = selectSinglePropertyFrom(from,from,ALL);
                    recieve(to,property,from);
                    paid += property.getWorth();
                    propertyWorth = propertyList.calculateWorthOfAll();

                // if the property is also not enough, the game will ask for the cards on hand
                }else {
                    println("Your bank has no money left(kekw), you have to pay with cards.");
                    println("Which card will you pay with?   ( "  + moneyFormat(amount - paid) + " left )");
                    Card card = selectAllCard(from,false);
                    cardList.take(card);
                    recieve(to,card,from);
                    paid += card.getWorth();
                }
            }
            prefixPlayer = to;
        }
    }

    //DEVELOPED BY: MORRO
    /**
     * Deposit money in bank
     *
     * @param player they player depositing
     * @param money the card to deposit
     */
    public void depositMoney(Player player, Card money){
        Player original = prefixPlayer;
        prefixPlayer = player;

        player.recieveCard(money);
        alert("Deposited "+money.getName() + " to your bank.");

        prefixPlayer = original;
    }

    //DEVELOPED BY: GLOM
    /**
     * Select a card
     *
     * @param cardList the card on hand
     * @param cancellable if there's "go back" option
     * @param from starting id of the card (including)
     * @param to ending id of the card (excluding)
     * @param by the player
     * @return the selected card
     */
    public Card selectCard(CardCounter cardList, boolean cancellable, int from, int to,Player by){
        Player original = prefixPlayer;
        prefixPlayer = by;
        // Use index and map to map the index of the card in the card list
        int index = 1;
        int[] map = new int[to - from + 1];
        if (cancellable)
            System.out.printf(CARD_FORMAT, prefix(), 0, "Go Back");
        // Show the options
        for (int i = from; i < Math.min(to, CardList.CARDS.length); i++) {
            if (cardList.getNumOf(i) > 0){
                // Map the index
                map[index] = i;
                Card card = cardList.getCardOf(i);
                System.out.printf(ITEM_FORMAT, prefix(), index++, card.getName(), cardList.getNumOf(i), card.getWorth());
            }
        }
        // Empty
        if (index == 1) {
            println("You have no this type card!");
        }
        int option = readInt("Your choice: ");
        while (option < 0 || option >= index){
            println("Invalid card index. Please choose again.");
            option = readInt("Your choice: ");
        }
        if (option == 0){
            return null;
        }
        // Get the card index
        int cardIndex = map[option];
        prefixPlayer = original;
        // Return the card
        return  cardList.getCardOf(cardIndex);
    }

    //DEVELOPED BY: GLOM
    /**
     * Select a card from the bank
     *
     * @param player the player who is going to select the card & whose bank is going to be selected from
     * @param cancellable whether the player can cancel the selection
     * @return the selected card
     */
    public Card selectCardInBank(Player player,boolean cancellable){
        return selectCard(player.getBank(),cancellable,0,Bank.MONEYS.length, player);
    }

    //DEVELOPED BY: GLOM
    /**
     * Select a card from the card list
     *
     * @param player the player who is going to select the card & whose card list is going to be selected from
     * @param cancellable whether the player can cancel the selection
     * @return the selected card
     */
    public Card selectAllCard(Player player,boolean cancellable){
        return selectCard(player.getCardList(),cancellable,0,CardList.CARDS.length, player);
    }

    //DEVELOPED BY: GLOM
    /**
     * Select a player from the game state
     *
     * @param by the player who is going to select the player
     * @param state the game state
     * @return the selected player
     */
    public Player selectPlayer(Player by, GameState state){
        Player original = prefixPlayer;
        prefixPlayer = by;

        Player[] players = state.getAllPlayers();
        int[] map = new int[players.length];
        int index = 1;
        for (int i = 0, playersLength = players.length; i < playersLength; i++) {
            Player player = players[i];
            //Exclude the player itself
            if (player != by) {
                // Map the index
                map[index] = i;
                println(index++ + ". " + player.getName());
            }
        }
        int option = readInt("Please select a player: ");
        while (option < 1 || option >= index){
            println("Invalid player index. Please choose again: ");
            option = readInt("Please select a player: ");
        }
        prefixPlayer = original;
        return players[map[option]];
    }

    //DEVELOPED BY: GLOM
    /**
     * Select a property from a player
     *
     * @param target which player is going to be select from
     * @param filter 0: all properties, 1: only completed properties，2: only incomplete properties
     * @return the selected properties
     */
    public Properties selectProperties(Player target,Player by,byte filter){
        Player original = prefixPlayer;
        prefixPlayer = by;
        println("Which color do you want to select?");
        int index = 1;
        int[] map = new int[Color.UNIVERSAL.length+1];
        for (int i = 0; i < Color.UNIVERSAL.length; i++) {
            Properties properties = target.getPropertyList().getProperties(Color.UNIVERSAL[i]);
            int maxLevel = Color.UNIVERSAL[i].getMaxLevel();
            int level = properties.getSize();
            boolean condition;
            // Filter the properties
            switch (filter){
                case COMPLETED:  // only completed properties
                    condition = level == maxLevel;
                    break;
                case INCOMPLETE: // only incomplete properties
                    condition = level != maxLevel;
                    break;
                default: // all properties
                    condition = true;
            }
            // Exclude the empty properties
            if (condition && level > 0) {
                // Map the index
                map[index] = i;
                displayProperties(properties,index++);
            }
        }
        // Empty
        if (index == 1) {
            return  null;
        }
        int option = readInt("Please select a color: ");
        while (option < 1 || option >= index){
            println("Invalid color index. Please choose again: ");
            option = readInt("Please select a color: ");
        }
        prefixPlayer = original;
        return target.getPropertyList().getProperties(Color.UNIVERSAL[map[option]]);
    }

    //DEVELOPED BY: GLOM
    /**
     * Select a property from a player
     *
     * @param target which player is going to be select from
     * @param filter 0: all properties, 1: only completed properties，2: only incomplete properties
     * @return the selected property
     */
    public Property selectSinglePropertyFrom(Player target,Player by, byte filter){
        // Select the properties first
        Properties properties = selectProperties(target,by,filter);
        // If the properties is null, return null
        if(properties == null) return null;
        Player original = prefixPlayer;
        prefixPlayer = by;
        String name;
        if(target == by) {
            name = "you";
        } else {
            name = target.getName();
        }

        println("Which property do you want to select from "+ name +" ?");
        int[] map = new int[properties.getSize()+1];
        int index = 1;
        for (int i = 0; i < properties.getSize(); i++) {
            // Map the index
            map[index] = i;
            Property property = properties.getData()[i];
            showColors(index++,property.getColors());
        }
        int option = readInt("Please select a property: ");
        while (option < 1 || option >= index){
            println("Invalid property index. Please choose again: ");
            option = readInt("Please select a property: ");
        }
        prefixPlayer = original;
        return properties.take(map[option]);
    }

    //DEVELOPED BY: MORRO
    /**
     * Select an action card from a player's card list (by the player itself)
     *
     * @param player the player who is going to select the property & whose property is going to be selected from
     * @param cancellable whether the player can cancel the selection
     * @return the selected property
     */
    public PerformableCard selectActionCard(Player player,boolean cancellable){
        return (PerformableCard) selectCard(player.getCardList(),cancellable,CardList.ACTION_START,CardList.ACTION_END, player);
    }

    //DEVELOPED BY: MORRO
    /**
     * Select a property card from a player's card list (by the player itself)
     *
     * @param player the player who is going to select the property & whose property is going to be selected from
     * @param cancellable whether the player can cancel the selection
     * @return the selected property
     */
    public PerformableCard selectPropertyCard(Player player,boolean cancellable){
        return (PerformableCard) selectCard(player.getCardList(),cancellable,CardList.PROPERTY_START,CardList.PROPERTY_END, player);
    }

    //DEVELOPED BY: MORRO
    /**
     * Select a rent card from a player's card list (by the player itself)
     *
     * @param player the player who is going to select the property & whose property is going to be selected from
     * @param cancellable whether the player can cancel the selection
     * @return the selected property
     */
    public PerformableCard selectRentCard(Player player,boolean cancellable){
        return (PerformableCard) selectCard(player.getCardList(),cancellable,CardList.RENT_START,CardList.RENT_END, player);
    }

    //DEVELOPED BY: MORRO
    /**
     * Ask player to choose a color
     *
     * @param player the player that'll be choosing the color
     * @param colors the array of colors
     * @return the color the player choose
     */
    public Color chooseColor(Player player,Color[] colors,byte filter){
        /*
         * Change current player for the player to know that it's other player's action
         * If player is not current player
         */
        Player temp = prefixPlayer;
        prefixPlayer = player;

        // Filter the colors
        Color[] result;
        {
            PropertyList propertyList = player.getPropertyList();
            Color[] filtered = new Color[colors.length];
            int count = 0;
            switch (filter){
                case COLOR_FILTER_INCOMPLETE: // Only the incomplete colors
                    for (int i = 0; i < colors.length; i++) {
                        Properties properties = propertyList.getProperties(colors[i]);
                        if (!properties.isCompleted()) {
                            filtered[count++] = colors[i];
                        }
                    }
                    break;
                case COLOR_FILTER_OWNED: // Only the owned colors
                    for (int i = 0; i < colors.length; i++) {
                        Properties properties = propertyList.getProperties(colors[i]);
                        if (properties.getSize() > 0) {
                            filtered[count++] = colors[i];
                        }
                    }
                    break;
                default: // All colors
                    filtered = colors;
                    break;
            }
            result = new Color[count];
            for (int i = 0; i < count; i++) {
                result[i] = filtered[i];
            }
        }

        int minOption = 0;
        if (filter == COLOR_FILTER_INCOMPLETE){
            println("To set a property:");
        }
        println("0. Put the card back on hand (Go Back)");

        //Show all the color options available
        for (int i = 0; i < result.length; i++) {
            Color color = result[i];
            Properties properties = player.getPropertyList().getProperties(color);
            if (filter == COLOR_FILTER_OWNED){
                System.out.printf(COLOR_MONEY_FORMAT, prefix(),i+1,color.getName(),properties.calculateWorth());
            } else {
                System.out.printf(COLOR_LEVEL_FORMAT, prefix(),i+1,color.getName(),properties.getSize(),color.getMaxLevel(),properties.getSize()+1,color.getMaxLevel());
            }
            System.out.println();
        }
        int option = readInt( player.getName() +", please select a color: ");

        if (option < minOption || option > result.length){
            println("Invalid color index. Please choose again: ");
            prefixPlayer = temp;
            return chooseColor(player,result, COLOR_FILTER_ALL);
        }
        prefixPlayer = temp;
        if (option == 0) return null;
        return result[option-1];
    }

    //DEVELOPED BY: MORRO
    /**
     * Display the bank of the player
     *
     * @param player the player
     * @param property the property
     */
    public void setPropertyOrDrop(Player player, Property property){
        Color chosen = chooseColor(player,property.getColors(), COLOR_FILTER_INCOMPLETE);
        // If the player doesn't choose any color
        if(chosen == null){
            // Put the property on the table (card stack)
            game.getCardStack().add(property);
            alert("You drop a " + property.getName() + ".");
            return;
        }
        // Otherwise, set the property
        player.getPropertyList().addProperty(property, chosen);
    }

    //DEVELOPED BY: MORRO
    /**
     * Ask the player to set the property
     *
     * @param player the player
     * @param property the property
     * @return if the property is successfully set
     */
    public boolean setProperty(Player player, Property property){
        //filter completed colors
        Color chosen = chooseColor(player,property.getColors(), COLOR_FILTER_INCOMPLETE);
        // If the player doesn't choose any color
        if(chosen == null){
            // Put the property in the card list (player's hand)
            player.getCardList().add(property);
            return false;
        }
        // Otherwise, set the property
        Properties properties =  player.getPropertyList().getProperties(chosen);
        properties.addProperty(property);
        alert(property.getName()  +" successfully setted as " + chosen.getName() + ", ( "+properties.getSize() + " / " + chosen.getMaxLevel() + " )");
        return true;
    }

    //DEVELOPED BY: GLOM
    /**
     * Draw a card from the card stack
     *
     * @param player the player who is going to draw the card
     */
    public void drawCard(Player player){
        Player temp = prefixPlayer;
        prefixPlayer = player;
        alert("You draw a " + player.getCardList().draw().getName());
        prefixPlayer = temp;
    }

    //DEVELOPED BY: GLOM
    /**
     * Display a message to the player, and wait for the player to press enter
     *
     * @param message the message to be displayed
     */
    public void alert(String message){
        println(message);
        waitForPlayer();
    }

    //DEVELOPED BY: GLOM
    /**
     * Display a message to the player
     *
     * @param message the message to be displayed
     */
    public void print(String message){
        System.out.printf(MESSAGE_FORMAT, prefix(), message);
    }

    //DEVELOPED BY: GLOM
    /**
     * Display a message to the player with a new line
     *
     * @param message the message to be displayed
     */
    public void println(String message){
        System.out.printf(MESSAGE_FORMAT + "%n", prefix(), message);
    }

    //DEVELOPED BY: GLOM
    /**
     * Ask the player if they want to double the rent
     *
     * @param player the player
     * @return if the player want to double the rent
     */
    public boolean doubleTheRent(Player player){
        Player original = prefixPlayer;
        prefixPlayer = player;
        CardList cardList = player.getCardList();
        int numOfDoubleRent = cardList.getNumOfDoubleTheRent();
        // If the player doesn't have any Double The Rent card
        if(numOfDoubleRent < 1) {
            prefixPlayer = original;
            return false;
        }
        println("You have " + numOfDoubleRent + " of Double The Rent! , do you want to use it?");
        println("1. Yes");
        println("2. No");
        int option = readInt("Your choice: ");
        if(option == 1){
            cardList.take(DoubleTheRent.DOUBLE_THE_RENT);
            prefixPlayer = original;
            return true;
        }
        prefixPlayer = original;
        return false;
    }

    //DEVELOPED BY: GLOM
    /**
     * Ask the player if they want to no the action
     *
     * @param player the player
     * @param command the command
     * @return if the player want to refuse the action
     */
    public boolean refuseByNo(Player player, String command){
        Player original = prefixPlayer;
        prefixPlayer = player;
        CardList cardList = player.getCardList();
        int numOfNo = cardList.getNumOfNo();
        // If the player doesn't have any Just Say No card
        if(numOfNo < 1) {
            prefixPlayer = original;
            return false;
        }
        println("You are being asked to " + command);
        println("You have " + numOfNo + " of Just Say NO! , do you want to use it?");
        println("1. Yes");
        println("2. No");
        int option = readInt("Your choice: ");
        if(option == 1){
            cardList.take(No.NO);
            prefixPlayer = original;
            return true;
        }
        prefixPlayer = original;
        return false;
    }

    //DEVELOPED BY: GLOM
    /**
     * Wait for the player to press enter
     */
    private void waitForPlayer(){
       print("Press enter to continue...");
       input.nextLine();
    }

    /**
     * Get the prefix of the message  (the prefix player's name)
     *
     * @return the prefix
     */
    private String prefix(){
        return prefixPlayer.getName();
    }

    //DEVELOPED BY: GLOM
    /**
     * Display the properties
     *
     * @param properties the properties
     * @param index the index to be displayed
     */
    private void displayProperties(Properties properties,int index){
        Color color = properties.getColor();
        int num = properties.getSize();
        if(index > 0){
            System.out.printf(PROPERTY_FORMAT, prefix(), index, color.getName(), num, color.getMaxLevel(),properties.calculateWorth());
            for (int j = 0; j < properties.getSize() ; j++) {
                Property property = properties.getData()[j];
                showColors(0,property.getColors());
            }
        }else{
            System.out.printf(PROPERTY_NO_INDEX_FORMAT, prefix(), color.getName(), num, color.getMaxLevel(),properties.calculateWorth());
            for (int j = 0; j < properties.getSize() ; j++) {
                Property property = properties.getData()[j];
                showColors(j+1,property.getColors());
            }
        }
    }

    //DEVELOPED BY: GLOM
    /**
     * Show the colors
     *
     * @param index the index
     * @param colors the colors
     */
    private void showColors(int index, Color[] colors){
        if (index > 0){
            if (colors == Color.UNIVERSAL) {
                System.out.printf(PROPERTIES_FORMAT, prefix(), index, "Universal");
                return;
            }
            String name = colors[0].getName();
            for (int k = 1; k < colors.length; k++) {
                name += " & " + colors[k].getName();
            }
            System.out.printf(PROPERTIES_FORMAT, prefix(), index, name);
        } else {
            if (colors == Color.UNIVERSAL) {
                System.out.printf(PROPERTIES_NO_INDEX_FORMAT, prefix(), "Universal");
                return;
            }
            String name = colors[0].getName();
            for (int k = 1; k < colors.length; k++) {
                name += " & " + colors[k].getName();
            }
            System.out.printf(PROPERTIES_NO_INDEX_FORMAT, prefix(), name);
        }
    }

    //DEVELOPED BY: GLOM
    /**
     * Display the property list
     *
     * @param propertyList the property list
     */
    private void displayPropertyList(PropertyList propertyList){
        System.out.printf(PROPERTY_HEAD, prefix());
        boolean empty = true;
        for (int i = 0; i < Color.UNIVERSAL.length; i++) {
            Color color = Color.UNIVERSAL[i];
            Properties properties = propertyList.getProperties(color);
            if (properties.getSize() > 0) {
                empty = false;
                displayProperties(properties,0);
            }
        }
        if (empty)  System.out.printf(PROPERTY_EMPTY, prefix());
    }

    //DEVELOPED BY: GLOM
    /**
     * Display the bank
     *
     * @param bank the bank
     */
    private void displayBank(Bank bank) {
        System.out.printf(BANK_HEAD, prefix(), bank.calculateTotalWorth());
        boolean empty = true;
        for (int i = 0; i < Bank.MONEYS.length; i++) {
            Money money = Bank.MONEYS[i];
            int num = bank.getNumOf(money);
            if (num > 0) {
                empty = false;
                System.out.printf(MONEY_FORMAT, prefix(),  money.getName(), num);
            }
        }
        if (empty) System.out.printf(BANK_EMPTY, prefix());
    }
}
