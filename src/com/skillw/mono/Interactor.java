package com.skillw.mono;

import com.skillw.mono.card.Card;
import com.skillw.mono.card.Money;
import com.skillw.mono.card.PerformableCard;
import com.skillw.mono.game.*;
import com.skillw.mono.util.CardCounter;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Interactor {
    private final Scanner input;
    private final String itemFormat;
    private final String propertyFormat;

    public static final Card GO_BACK = null;

    public Interactor(){
        input = new Scanner(System.in);
        itemFormat = "%d. %-25s    x%-2d    ~ $ %dM%n";
        propertyFormat = "%d. %-11s    x%-2d/%-2d    - $ %dM%n";
    }


    public void displayHeader(){
        System.out.println("======================================");
        System.out.println("|         Welcome to Monopoly         ");
        System.out.println("======================================");
    }

    public void emptyGap(){
        for(int i = 0; i < 30; i ++){
            System.out.println();
        }
    }

    private void displayPropertyList(PropertyList properties){
        int index = 1;
        for (int i = 0; i < Color.UNIVERSAL.length; i++) {
            Color color = Color.UNIVERSAL[i];
            int num = properties.getNumOf(color);
            if (num > 0) {
                System.out.printf(propertyFormat, index++, color.getName(), num,color.getMaxLevel(), properties.calculateWorthOf(color));
            }
        }
    }

    private void displayBank(Bank money){

    }

    public void displayCards(GameState state){
        Player[] players = state.getAllPlayers();
        for (int i=0; i<players.length; i++){
            displayPropertyList(players[i].getPropertyList());
            displayBank(players[i].getBank());
        }
    }

    public String moneyOf(int amount){
        return "$ " + amount + "M";
    }

    /**
     * Register the players in the game
     * @return   - the array of players
     */
    public String[] registerPlayers(){
        System.out.println("Please enter players name, enter 'done' to finish.");
        String[] names = new String[6];
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



    public CardStack askToPayWith(Player from, Player to, int amount){
        CardStack cardStack = new CardStack();
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
                    cardStack.add(card);
                }
            }
            return cardStack;
        }
        System.out.println(from.getName() + " , you have to pay " + moneyOf(amount));
        int paid = 0;
        while (paid < amount){
            if (bankWorth > 0){
                System.out.println("You have " + moneyOf(bankWorth) + " in bank, you have to pay with money in bank first.");
                System.out.println("How much money will you pay with money in bank?   ( " + moneyOf(amount - paid) + " left )");
                Money money  = (Money) selectAllCard(bank,false);
                bank.take(money);
                cardStack.add(money);
                paid += money.getWorth();
            } else {
                System.out.println("Your bank has no money left, you have to pay with cards.");
                System.out.println("Which card will you pay with?   ( "  + moneyOf(amount - paid) + " left )");
                Card card = selectAllCard(cardList,false);
                cardList.take(card);
                cardStack.add(card);
                paid += card.getWorth();
            }
        }
        return cardStack;
    }

    public Card selectCard(CardCounter cardList, boolean cancellable, int from, int to){
        int index = 1;
        int[] map = new int[to - from];
        if (cancellable)
            System.out.printf("%d. %-25s%n", 0, "Go Back");
        for (int i = from; i < Math.min(to, CardList.CARDS.length); i++) {
            if (cardList.getNumOf(i) > 0){
                map[index] = i;
                Card card = CardList.CARDS[i];
                System.out.printf(itemFormat, index++, card.getName(), cardList.getNumOf(i), card.getWorth());
            }
        }
        /**
         * What player sees:
         *
         * index
         * ↓
         * 1. xxx  1
         * 2. yyy  2
         * 3. aaa  3
         */
        /**
         * What program sees:
         *
         * i     card number
         * ↓      ↓
         * 0. ccc 0
         * 1. xxx 1
         * 2. ddd 0
         * 3. yyy 2
         * 4. aaa 3
         */
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


    public Card selectAllCard(CardCounter cardList,boolean cancellable){
        return selectCard(cardList,cancellable,0,cardList.size());
    }

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

    public Player selectPlayer(GameState state, Player by){
        Player[] players = state.getAllPlayers();

        int[] map = new int[players.length-1];
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


    public Color chooseColor(Player player,Color[] colors){
        for (int i = 0; i < colors.length; i++) {
            Color color = colors[i];
            System.out.println(i+1 + ". " + color.getName());
        }
        int option = readInt( player.getName() +", please select a color: ");
        if (option < 1 || option >= colors.length){
            System.out.println("Invalid color index. Please choose again: ");
            return chooseColor(player,colors);
        }
        return colors[option-1];
    }
}
