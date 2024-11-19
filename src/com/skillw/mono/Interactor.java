package com.skillw.mono;

import com.skillw.mono.card.Card;
import com.skillw.mono.card.Money;
import com.skillw.mono.card.PerformableCard;
import com.skillw.mono.card.Property;
import com.skillw.mono.card.action.DoubleTheRent;
import com.skillw.mono.card.action.No;
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
    private static final String MESSAGE_FORMAT = PREFIX + "%s";

    private static final Card GO_BACK = null;

    public static final byte ALL = 0;
    public static final byte COMPLETED = 1;
    public static final byte INCOMPLETE = 2;

    private final Scanner input;
    private GameState game = null;
    private Player currentPlayer = null;

    //DEVELOPED BY: MORRO
    public Interactor(){
        this.input = new Scanner(System.in);
    }

    public void init(GameState game){
        this.game = game;
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
    public void displayState(){
        Player[] players = game.getAllPlayers();
        for (int i=0; i<players.length; i++){
            println(players[i].getName() + ":");
            displayPropertyList(players[i].getPropertyList());
            displayBank(players[i].getBank());
            println("");
        }
    }

    //DEVELOPED BY: MORRO
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    //DEVELOPED BY: MORRO
    public String moneyFormat(int amount){
        return "$ " + amount + "M";
    }

    //DEVELOPED BY: MORRO
    public int readInt(String prompt){
        try {
            print(prompt);
            return input.nextInt();
        } catch (InputMismatchException e){
            println("Invalid input, please enter a number!");
            input.nextLine();
            return readInt(prompt);
        }
    }

    //DEVELOPED BY: MORRO
    /**
     * Register the players in the game
     * @return   - the array of players
     */
    public String[] registerPlayers(){
        System.out.println("Please enter players name, enter 'done' to finish.");
        String[] names = new String[5];
        int count = 0;
        while (count < 5){
            System.out.print("Player " + (count+1) + ": ");
            String name = input.next();
            if (name.equals("done")){
                if (count < 2){
                    System.out.println("At least 2 players are needed to start the game.");
                } else {
                    System.out.println("All players have been registered.");
                    break;
                }
            } else for (int i = 0; i < names.length; i++) {
                if (names[i] == null){
                    names[i] = name;
                    count++;
                    System.out.println("Player " + name + " has been registered.");
                    break;
                }
            }
        }
        String[] result = new String[count];
        for (int i = 0; i < count; i++) {
            result[i] = names[i];
        }
        return result;
    }

    //DEVELOPED BY: MORRO
    public void displayUniqueCardNum(Player player){
        println("Double The Rent (" + player.getCardList().getNumOfDoubleRent() + ")" + "  Just Say No (" + player.getCardList().getNumOfNo() + ")");
    }

    //DEVELOPED BY: MORRO
    public void askToPay(Player from, Player to, int amount){
        Bank bank = from.getBank();
        PropertyList propertyList = from.getPropertyList();
        CardList cardList = from.getCardList();
        int bankWorth = bank.calculateTotalWorth();
        int propertyWorth = propertyList.calculateWorthOfAll();
        int cardWorth = cardList.calculateTotalWorth();
        if (bankWorth + cardWorth + propertyWorth < amount){
            println(from.getName() + " don't have enough money & cards to pay, so you will take all cards from " + from.getName());
            for (int i = 0; i < cardList.size(); i++) {
                Card card = CardList.CARDS[i];
                int num = cardList.clearOf(i);
                for (int j = 0; j < num; j++) {
                    to.recieveCard(card);
                }
            }
            for (int i = 0; i < Bank.MONEYS.length; i++) {
                Money money = Bank.MONEYS[i];
                int num = bank.clearOf(money);
                for (int j = 0; j < num; j++) {
                    to.recieveCard(money);
                }
            }
            for (int i = 0; i < Color.UNIVERSAL.length; i++) {
                Properties properties = propertyList.getProperties(Color.UNIVERSAL[i]);
                for (int j = 0; j < properties.getSize(); j++) {
                    Property property = properties.take(j);
                    setProperty(to,property);
                }
            }
        } else {
            currentPlayer = from;
            println(from.getName() + " , you have to pay " + moneyFormat(amount) + " to " + to.getName());
            int paid = 0;
            while (paid < amount){
                if (bankWorth > 0){
                    println("You have " + moneyFormat(bankWorth) + " in bank, you have to pay with money in bank first.");
                    println("How much money will you pay with money in bank?   ( " + moneyFormat(amount - paid) + " left )");
                    Money money  = (Money) selectCardInBank(from,false);
                    bank.take(money);
                    to.recieveCard(money);
                    paid += money.getWorth();
                } else if (propertyWorth > 0){
                    println("You have " + moneyFormat(propertyWorth) + " in properties, you have to pay with properties.");
                    println("Which property will you pay with?   ( " + moneyFormat(amount - paid) + " left )");
                    Property property = selectSinglePropertyFrom(from,from,ALL);
                    setProperty(to,property);
                    paid += property.getWorth();
                }else {
                    println("Your bank has no money left, you have to pay with cards.");
                    println("Which card will you pay with?   ( "  + moneyFormat(amount - paid) + " left )");
                    Card card = selectAllCard(from,false);
                    cardList.take(card);
                    to.recieveCard(card);
                    paid += card.getWorth();
                }
            }
            currentPlayer = to;
        }
    }

    //DEVELOPED BY: MORRO
    public void depositMoney(Player player, Card money){
        player.recieveCard(money);
    }

    //DEVELOPED BY: GLOM
    public Card selectCard(CardCounter cardList, boolean cancellable, int from, int to,Player by){
        Player original = currentPlayer;
        currentPlayer = by;
        int index = 1;
        int[] map = new int[to - from + 1];
        if (cancellable)
            System.out.printf(CARD_FORMAT,currentPlayer.getName(), 0, "Go Back");
        for (int i = from; i < Math.min(to, CardList.CARDS.length); i++) {
            if (cardList.getNumOf(i) > 0){
                map[index] = i;
                Card card = CardList.CARDS[i];
                System.out.printf(ITEM_FORMAT,currentPlayer.getName(), index++, card.getName(), cardList.getNumOf(i), card.getWorth());
            }
        }
        if (index == 1) {
            println("You have no this type card!");
        }
        int option = readInt("Your choice: ");
        while (option < 0 || option >= index){
            println("Invalid card index. Please choose again.");
            option = readInt("Your choice: ");
        }
        if (option == 0){
            return GO_BACK;
        }
        int cardIndex = map[option];
        currentPlayer = original;
        return  CardList.CARDS[cardIndex];
    }

    //DEVELOPED BY: GLOM
    public Card selectCardInBank(Player player,boolean cancellable){
        return selectCard(player.getBank(),cancellable,0,Bank.MONEYS.length, player);
    }

    //DEVELOPED BY: GLOM
    public Card selectAllCard(Player player,boolean cancellable){
        return selectCard(player.getCardList(),cancellable,0,CardList.CARDS.length, player);
    }

    //DEVELOPED BY: GLOM
    public Player selectPlayer(Player by, GameState state){
        Player original = currentPlayer;
        currentPlayer = by;

        Player[] players = state.getAllPlayers();

        int[] map = new int[players.length];
        int index = 1;
        for (int i = 0, playersLength = players.length; i < playersLength; i++) {
            Player player = players[i];
            if (player != by) {
                map[index] = i;
                println(index++ + ". " + player.getName());
            }
        }
        int option = readInt("Please select a player: ");
        while (option < 1 || option >= index){
            println("Invalid player index. Please choose again: ");
            option = readInt("Please select a player: ");
        }
        currentPlayer = original;
        return players[map[option]];
    }

    //DEVELOPED BY: GLOM
    /**
     * Select a property from a player
     * @param target which player is going to be select from
     * @param filter 0: all properties, 1: only completed properties，2: only incomplete properties
     * @return the selected properties
     */
    public Properties selectProperties(Player target,Player by,byte filter){
        Player original = currentPlayer;
        currentPlayer = by;
        println("Which color do you want to select?");
        int index = 1;
        int[] map = new int[Color.UNIVERSAL.length+1];
        for (int i = 0; i < Color.UNIVERSAL.length; i++) {
            Properties properties = target.getPropertyList().getProperties(Color.UNIVERSAL[i]);
            int maxLevel = Color.UNIVERSAL[i].getMaxLevel();
            int level = properties.getSize();
            boolean condition;
            switch (filter){
                case COMPLETED:  // only completed properties
                    condition = level == maxLevel;
                    break;
                case INCOMPLETE:
                    condition = level != maxLevel;
                    break;
                default:
                    condition = true;
            }
            if (condition && level > 0) {
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
        currentPlayer = original;
        return target.getPropertyList().getProperties(Color.UNIVERSAL[map[option]]);
    }

    //DEVELOPED BY: GLOM
    /**
     * Select a property from a player
     * @param target which player is going to be select from
     * @param filter 0: all properties, 1: only completed properties，2: only incomplete properties
     * @return the selected property
     */
    public Property selectSinglePropertyFrom(Player target,Player by, byte filter){
        Properties properties = selectProperties(target,by,filter);
        if(properties == null) return null;
        Player original = currentPlayer;
        currentPlayer = by;
        println("Which property do you want to select?");
        int[] map = new int[properties.getSize()+1];
        int index = 1;
        for (int i = 0; i < properties.getSize(); i++) {
            Property property = properties.getData()[i];
            map[index] = i;
            showColors(index++,property.getColors());
        }
        int option = readInt("Please select a property: ");
        while (option < 1 || option >= index){
            println("Invalid property index. Please choose again: ");
            option = readInt("Please select a property: ");
        }
        currentPlayer = original;
        return properties.take(map[option]);
    }

    //DEVELOPED BY: MORRO
    public PerformableCard selectActionCard(Player player,boolean cancellable){
        return (PerformableCard) selectCard(player.getCardList(),cancellable,CardList.ACTION_START,CardList.ACTION_END, player);
    }

    //DEVELOPED BY: MORRO
    public PerformableCard selectPropertyCard(Player player,boolean cancellable){
        return (PerformableCard) selectCard(player.getCardList(),cancellable,CardList.PROPERTY_START,CardList.PROPERTY_END, player);
    }

    //DEVELOPED BY: MORRO
    public PerformableCard selectRentCard(Player player,boolean cancellable){
        return (PerformableCard) selectCard(player.getCardList(),cancellable,CardList.RENT_START,CardList.RENT_END, player);
    }

    public final static byte COLOR_FILTER_ALL = 0;
    public final static byte COLOR_FILTER_OWNED = 1;
    public final static byte COLOR_FILTER_INCOMPLETED = 2;


    //DEVELOPED BY: MORRO
    /**
     * Ask player to choose a color
     * @param player the player that'll be choosing the color
     * @param colors the array of colors
     * @return the color the player choose
     */
    public Color chooseColor(Player player,Color[] colors,byte filter){
        /*
         * Change current player for the player to know that it's other player's action
         * If player is not current player
         */
        Player temp = currentPlayer;
        currentPlayer = player;
        Color[] result;
        {
            PropertyList propertyList = player.getPropertyList();
            Color[] filtered = new Color[colors.length];
            int count = 0;
            switch (filter){
                case COLOR_FILTER_INCOMPLETED:
                    for (int i = 0; i < colors.length; i++) {
                        Properties properties = propertyList.getProperties(colors[i]);
                        if (!properties.isCompleted()) {
                            filtered[count++] = colors[i];
                        }
                    }
                    break;
                case COLOR_FILTER_OWNED:
                    for (int i = 0; i < colors.length; i++) {
                        Properties properties = propertyList.getProperties(colors[i]);
                        if (properties.getSize() > 0) {
                            filtered[count++] = colors[i];
                        }
                    }
                    break;
                default:
                    filtered = colors;
                    break;
            }
            result = new Color[count];
            for (int i = 0; i < count; i++) {
                result[i] = filtered[i];
            }
        }

        int minOption = 1;
        if (filter != COLOR_FILTER_INCOMPLETED){
            minOption = 0;
            println("0. Go Back");
        }
        //Show all the color options available
        for (int i = 0; i < result.length; i++) {
            Color color = result[i];
            print(i+1 + ". " + color.getName());
            if (filter == COLOR_FILTER_OWNED){
                Properties properties = player.getPropertyList().getProperties(color);
                System.out.printf("  ( $" + properties.calculateWorth() + "M )");
            }
            System.out.println();
        }
        int option = readInt( player.getName() +", please select a color: ");

        if (option < minOption || option > result.length){
            println("Invalid color index. Please choose again: ");
            return chooseColor(player,result, COLOR_FILTER_ALL);
        }
        currentPlayer = temp;
        if (option == 0) return null;
        return result[option-1];
    }

    //DEVELOPED BY: MORRO
    public void setProperty(Player player, Property property){
        //filter completed colors
        Color chosen = chooseColor(player,property.getColors(), COLOR_FILTER_INCOMPLETED);
        player.getPropertyList().addProperty(property, chosen);
    }

    public void alert(String message){
        println(message);
        waitForPlayer();
    }

    public void print(String message){
        System.out.printf(MESSAGE_FORMAT, currentPlayer.getName(), message);
    }

    public void println(String message){
        System.out.printf(MESSAGE_FORMAT + "%n", currentPlayer.getName(), message);
    }


    //DEVELOPED BY: MORRO
    private void waitForPlayer(){
       print("Press enter to continue...");
        input.nextLine();
        input.nextLine();
    }

    //DEVELOPED BY: GLOM
    private void displayProperties(Properties properties,int index){
        Color color = properties.getColor();
        int num = properties.getSize();
        if(index > 0){
            System.out.printf(PROPERTY_FORMAT,currentPlayer.getName(), index, color.getName(), num, color.getMaxLevel(),properties.calculateWorth());
            for (int j = 0; j < properties.getSize() ; j++) {
                Property property = properties.getData()[j];
                showColors(0,property.getColors());
            }
        }else{
            System.out.printf(PROPERTY_NO_INDEX_FORMAT,currentPlayer.getName(), color.getName(), num, color.getMaxLevel(),properties.calculateWorth());
            for (int j = 0; j < properties.getSize() ; j++) {
                Property property = properties.getData()[j];
                showColors(j+1,property.getColors());
            }
        }
    }

    //DEVELOPED BY: GLOM
    private void showColors(int index, Color[] colors){
        if (index > 0){
            if (colors == Color.UNIVERSAL) {
                System.out.printf(PROPERTIES_FORMAT,currentPlayer.getName(), index, "Universal");
                return;
            }
            String name = colors[0].getName();
            for (int k = 1; k < colors.length; k++) {
                name += " & " + colors[k].getName();
            }
            System.out.printf(PROPERTIES_FORMAT,currentPlayer.getName(), index, name);
        } else {
            if (colors == Color.UNIVERSAL) {
                System.out.printf(PROPERTIES_NO_INDEX_FORMAT,currentPlayer.getName(), "Universal");
                return;
            }
            String name = colors[0].getName();
            for (int k = 1; k < colors.length; k++) {
                name += " & " + colors[k].getName();
            }
            System.out.printf(PROPERTIES_NO_INDEX_FORMAT,currentPlayer.getName(), name);
        }
    }

    //DEVELOPED BY: GLOM
    private void displayPropertyList(PropertyList propertyList){
        System.out.printf(PROPERTY_HEAD,currentPlayer.getName());
        boolean empty = true;
        for (int i = 0; i < Color.UNIVERSAL.length; i++) {
            Color color = Color.UNIVERSAL[i];
            Properties properties = propertyList.getProperties(color);
            if (properties.getSize() > 0) {
                empty = false;
                displayProperties(properties,0);
            }
        }
        if (empty)  System.out.printf(PROPERTY_EMPTY,currentPlayer.getName());
    }

    //DEVELOPED BY: GLOM
    private void displayBank(Bank bank) {
        System.out.printf(BANK_HEAD,currentPlayer.getName(), bank.calculateTotalWorth());
        boolean empty = true;
        for (int i = 0; i < Bank.MONEYS.length; i++) {
            Money money = Bank.MONEYS[i];
            int num = bank.getNumOf(money);
            if (num > 0) {
                empty = false;
                System.out.printf(MONEY_FORMAT,currentPlayer.getName(),  money.getName(), num);
            }
        }
        if (empty) System.out.printf(BANK_EMPTY,currentPlayer.getName());
    }

    public boolean doubleTheRent(Player player){
        Player original = currentPlayer;
        currentPlayer = player;
        CardList cardList = player.getCardList();
        int numOfDoubleRent = cardList.getNumOfDoubleRent();
        if(numOfDoubleRent < 1) return false;
        println("You have " + numOfDoubleRent + " of Double The Rent! , do you want to use it?");
        println("1. Yes");
        println("2. No");
        int option = readInt("Your choice: ");
        if(option == 1){
            cardList.take(DoubleTheRent.DOUBLE_THE_RENT);
            currentPlayer = original;
            return true;
        }
        currentPlayer = original;
        return false;
    }

    public boolean refuseByNo(Player player, String command){
        Player original = currentPlayer;
        currentPlayer = player;
        CardList cardList = player.getCardList();
        int numOfNo = cardList.getNumOfNo();
        if(numOfNo < 1) return false;
        println("You are being asked to " + command);
        println("You have " + numOfNo + " of Just Say NO! , do you want to use it?");
        println("1. Yes");
        println("2. No");
        int option = readInt("Your choice: ");
        if(option == 1){
            cardList.take(No.NO);
            currentPlayer = original;
            return true;
        }
        currentPlayer = original;
        return false;
    }
}
