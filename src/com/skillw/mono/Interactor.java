package com.skillw.mono;

import com.skillw.mono.card.Card;
import com.skillw.mono.card.Money;
import com.skillw.mono.card.PerformableCard;
import com.skillw.mono.card.Property;
import com.skillw.mono.game.*;
import com.skillw.mono.util.CardCounter;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Interactor {
    private final Scanner input;
    private GameState game = null;
    private static final String ITEM_FORMAT = "%d. %-25s    x%-2d    ~ $ %dM %n";
    private static final String PROPERTY_HEAD = "    Properties: %n";
    private static final String PROPERTY_FORMAT = "    |-  %d. %-11s    ( %d / %d )    - $ %dM %n";
    private static final String PROPERTY_NO_INDEX_FORMAT = "    |-  %-11s    ( %d / %d )    - $ %dM %n";
    private static final String PROPERTIES_FORMAT = "        |-  %d. %s %n";
    private static final String PROPERTIES_NO_INDEX_FORMAT = "        |-    %s %n";
    private static final String PROPERTY_EMPTY = "    |-  No properties owned! %n";
    private static final String BANK_HEAD = "    Bank ( $ %d M ): %n";
    private static final String MONEY_FORMAT = "        |-  %-11s    x%-2d %n";
    private static final String BANK_EMPTY = "    |-  No money in bank! %n";
    private static final Card GO_BACK = null;

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
    private void displayProperties(Properties properties,int index){
        Color color = properties.getColor();
        int num = properties.getSize();
        if(index > 0){
            System.out.printf(PROPERTY_FORMAT, index, color.getName(), num, color.getMaxLevel(),properties.calculateWorth());
            for (int j = 0; j < properties.getSize() ; j++) {
                Property property = properties.getData()[j];
                showColors(0,property.getColors());
            }
        }else{
            System.out.printf(PROPERTY_NO_INDEX_FORMAT, color.getName(), num, color.getMaxLevel(),properties.calculateWorth());
            for (int j = 0; j < properties.getSize() ; j++) {
                Property property = properties.getData()[j];
                showColors(j+1,property.getColors());
            }
        }
    }

    //DEVELOPED BY: GLOM
    private void displayPropertyList(PropertyList propertyList){
        System.out.printf(PROPERTY_HEAD);
        boolean empty = true;
        for (int i = 0; i < Color.UNIVERSAL.length; i++) {
            Color color = Color.UNIVERSAL[i];
            Properties properties = propertyList.getProperties(color);
            if (properties.getSize() > 0) {
                empty = false;
                displayProperties(properties,0);
            }
        }
        if (empty)  System.out.printf(PROPERTY_EMPTY);
    }

    //DEVELOPED BY: GLOM
    private void displayBank(Bank bank) {
        System.out.printf(BANK_HEAD, bank.calculateWorth());
        boolean empty = true;
        for (int i = 0; i < Bank.MONEYS.length; i++) {
            Money money = Bank.MONEYS[i];
            int num = bank.getNumOf(money);
            if (num > 0) {
                empty = false;
                System.out.printf(MONEY_FORMAT,  money.getName(), num);
            }
        }
        if (empty) System.out.printf(BANK_EMPTY);
    }

    //DEVELOPED BY: GLOM
    public void displayCards(){
        Player[] players = game.getAllPlayers();
        for (int i=0; i<players.length; i++){
            System.out.println(players[i].getName() + ":");
            displayPropertyList(players[i].getPropertyList());
            displayBank(players[i].getBank());
            System.out.println();
        }
    }

    public String moneyOf(int amount){
        return "$ " + amount + "M";
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
    public int readInt(String prompt){
        try {
            System.out.print(prompt);
            return input.nextInt();
        } catch (InputMismatchException e){
            System.out.println("Invalid input, please enter a number!");
            input.nextLine();
            return readInt(prompt);
        }
    }

    public void askToPay(Player from, Player to, int amount){
        CardList with = new CardList(game);
        Bank bank = from.getBank();
        CardList cardList = from.getCardList();
        int bankWorth = bank.calculateWorth();
        int cardWorth = cardList.calculateWorth();
        if (bankWorth + cardWorth < amount){
            System.out.println(from.getName() + " don't have enough money & cards to pay, so " + to.getName() + " will take all cards from " + from.getName());
            for (int i = 0; i < cardList.size(); i++) {
                Card card = CardList.CARDS[i];
                int num = cardList.clearOf(i);
                for (int j = 0; j < num; j++) {
                    with.add(card);
                }
            }
            for (int i = 0; i < Bank.MONEYS.length; i++) {
                Money money = Bank.MONEYS[i];
                int num = bank.clearOf(money);
                for (int j = 0; j < num; j++) {
                    with.add(money);
                }
            }
        } else {
            System.out.println(from.getName() + " , you have to pay " + moneyOf(amount));
            int paid = 0;
            while (paid < amount){
                if (bankWorth > 0){
                    System.out.println("You have " + moneyOf(bankWorth) + " in bank, you have to pay with money in bank first.");
                    System.out.println("How much money will you pay with money in bank?   ( " + moneyOf(amount - paid) + " left )");
                    Money money  = (Money) selectAllCard(bank,false);
                    bank.take(money);
                    with.add(money);
                    paid += money.getWorth();
                } else {
                    System.out.println("Your bank has no money left, you have to pay with cards.");
                    System.out.println("Which card will you pay with?   ( "  + moneyOf(amount - paid) + " left )");
                    Card card = selectAllCard(cardList,false);
                    cardList.take(card);
                    with.add(card);
                    paid += card.getWorth();
                }
            }
        }
        to.recieveCards(with);
    }

    public void showCardStack(CardList cards){
        System.out.println("Card Stack:");
        for (int i = 0; i < cards.size(); i++) {
            Card card = CardList.CARDS[i];
            System.out.printf(ITEM_FORMAT, i+1, card.getName(), cards.getNumOf(i), card.getWorth());
        }
    }
    //DEVELOPED BY: MORRO
    public void depositMoney(Player player, Card money){
        player.recieveCard(money);
        System.out.println("Successfully deposited $"+money.getWorth());
    }

    //DEVELOPED BY: GLOM
    public Card selectCard(CardCounter cardList, boolean cancellable, int from, int to){
        int index = 1;
        int[] map = new int[to - from + 1];
        if (cancellable)
            System.out.printf("%d. %-25s%n", 0, "Go Back");
        for (int i = from; i < Math.min(to, CardList.CARDS.length); i++) {
            if (cardList.getNumOf(i) > 0){
                map[index] = i;
                Card card = CardList.CARDS[i];
                System.out.printf(ITEM_FORMAT, index++, card.getName(), cardList.getNumOf(i), card.getWorth());
            }
        }
        if (index == 1) {
            System.out.println("You have no this type card!");
        }
        int option = readInt("Your choice: ");
        while (option < 0 || option >= index){
            System.out.println("Invalid card index. Please choose again.");
            option = readInt("Your choice: ");
        }
        if (option == 0){
            return GO_BACK;
        }
        int cardIndex = map[option];
        return  CardList.CARDS[cardIndex];
    }

    //DEVELOPED BY: GLOM
    public Card selectAllCard(CardCounter cardList,boolean cancellable){
        return selectCard(cardList,cancellable,0,cardList.size());
    }

    //DEVELOPED BY: GLOM
    public Card selectAllCard(Player player,boolean cancellable){
        return selectCard(player.getCardList(),cancellable,0,CardList.CARDS.length);
    }
    public PerformableCard selectActionCard(Player player,boolean cancellable){
        return (PerformableCard) selectCard(player.getCardList(),cancellable,CardList.ACTION_START,CardList.ACTION_END);
    }

    public PerformableCard selectPropertyCard(Player player,boolean cancellable){
        return (PerformableCard) selectCard(player.getCardList(),cancellable,CardList.PROPERTY_START,CardList.PROPERTY_END);
    }

    public PerformableCard selectRentCard(Player player,boolean cancellable){
        return (PerformableCard) selectCard(player.getCardList(),cancellable,CardList.RENT_START,CardList.RENT_END);
    }

    public Money selectMoneyCard(Player player,boolean cancellable){
        return  (Money)selectCard(player.getCardList(),cancellable,CardList.MONEY_START,CardList.MONEY_END);
    }

    //DEVELOPED BY: GLOM
    public Player selectPlayer(Player by, GameState state){
        Player[] players = state.getAllPlayers();

        int[] map = new int[players.length];
        int index = 1;
        for (int i = 0, playersLength = players.length; i < playersLength; i++) {
            Player player = players[i];
            if (player != by) {
                map[index] = i;
                System.out.println(index++ + ". " + player.getName());
            }
        }
        int option = readInt("Please select a player: ");
        while (option < 1 || option >= index){
            System.out.println("Invalid player index. Please choose again: ");
            option = readInt("Please select a player: ");
        }
        return players[map[option]];
    }

    //DEVELOPED BY: MORRO
    /**
     * Select a property from a player
     * @param player the player that'll be choosing the color
     * @param colors the array of colors
     * @return the color the player choose
     */
    public Color chooseColor(Player player,Color[] colors){
        //Show all the color options available
        for (int i = 0; i < colors.length; i++) {
            Color color = colors[i];
            System.out.println(i+1 + ". " + color.getName());
        }
        int option = readInt( player.getName() +", please select a color: ");
        if (option < 1 || option > colors.length){
            System.out.println("Invalid color index. Please choose again: ");
            return chooseColor(player,colors);
        }
        return colors[option-1];
    }

    public static final byte ALL = 0;
    public static final byte COMPLETED = 1;
    public static final byte INCOMPLETE = 2;

    //DEVELOPED BY: GLOM
    /**
     * Select a property from a player
     * @param target which player is going to be select from
     * @param filter 0: all properties, 1: only completed properties，2: only incomplete properties
     * @return the selected properties
     */
    public Properties selectProperties(Player target,byte filter){
        System.out.println("Which color do you want to select?");
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
            System.out.println("Invalid color index. Please choose again: ");
            option = readInt("Please select a color: ");
        }
        return target.getPropertyList().getProperties(Color.UNIVERSAL[map[option]]);
    }

    //DEVELOPED BY: MORRO
    /**
     * Select a property from a player
     * @param target which player is going to be select from
     * @param filter 0: all properties, 1: only completed properties，2: only incomplete properties
     * @return the selected property
     */
    public Property selectSinglePropertyFrom(Player target, byte filter){
        Properties properties = selectProperties(target,filter);
        if(properties == null) return null;
        System.out.println("Which property do you want to select?");
        int[] map = new int[properties.getSize()+1];
        int index = 1;
        for (int i = 0; i < properties.getSize(); i++) {
            Property property = properties.getData()[i];
            map[index] = i;
            showColors(index++,property.getColors());
        }
        int option = readInt("Please select a property: ");
        while (option < 1 || option >= index){
            System.out.println("Invalid property index. Please choose again: ");
            option = readInt("Please select a property: ");
        }
        return properties.take(map[option]);
    }

    private void showColors(int index, Color[] colors){
        if (index > 0){
            if (colors == Color.UNIVERSAL) {
                System.out.printf(PROPERTIES_FORMAT, index, "Universal");
                return;
            }
            String name = colors[0].getName();
            for (int k = 1; k < colors.length; k++) {
                name += " & " + colors[k].getName();
            }
            System.out.printf(PROPERTIES_FORMAT, index, name);
        } else {
            if (colors == Color.UNIVERSAL) {
                System.out.printf(PROPERTIES_NO_INDEX_FORMAT, "Universal");
                return;
            }
            String name = colors[0].getName();
            for (int k = 1; k < colors.length; k++) {
                name += " & " + colors[k].getName();
            }
            System.out.printf(PROPERTIES_NO_INDEX_FORMAT, name);
        }
    }

}
