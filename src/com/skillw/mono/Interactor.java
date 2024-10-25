package com.skillw.mono;

import com.skillw.mono.card.Card;
import com.skillw.mono.card.Money;
import com.skillw.mono.game.Bank;
import com.skillw.mono.game.CardList;
import com.skillw.mono.game.Player;

import java.util.Scanner;

public class Interactor {
    private final Scanner input;
    private final String itemFormat;

    public Interactor(){
        input = new Scanner(System.in);
        itemFormat = "%d. %-13s    x$d    ~ $ %dM%n";
    }


    public void displayHeader(){
        System.out.println("======================================");
        System.out.println("|         Welcome to Monopoly         ");
        System.out.println("======================================");
    }

    public String moneyOf(int amount){
        return "$ " + amount + "M";
    }


    public void askForPay(Player player, int amount){
        Bank bank = player.getBank();
        CardList cardList = player.getCardList();
        int bankWorth = bank.calculateWorth();
        int cardWorth = cardList.calculateWorth();
        if (bankWorth + cardWorth < amount){
            System.out.println(player.getName() + " don't have enough money to pay.");
            return;
        }
        System.out.println(player.getName() + " , you have to pay " + moneyOf(amount));
        int paid = 0;
        while (paid < amount){
            System.out.println("What will you pay with? ( " + paid + "/" + amount + " )");
            System.out.println("1. Money in your bank " + "( " + bankWorth + " ).");
            System.out.println("2. Cards on your hand " + "( " + cardWorth + " ).");
            int choice = input.nextInt();
            while (choice != 1 && choice != 2){
                System.out.println("Invalid choice. Please choose again.");
                choice = input.nextInt();
            }
            switch (choice){
                case 1:{
                    System.out.println("How much money will you pay with money in bank?");
                    int index = 1;
                    int[] map = new int[Bank.MONIES.length];
                    for (int i = 0; i < Bank.MONIES.length; i++) {
                        if (bank.hasMoneyOf(i)){
                            map[index] = i;
                            Money money = Bank.MONIES[i];
                            System.out.printf(itemFormat, index++, money.getName(), bank.getMoneyOf(i), money.getWorth());
                        }
                    }
                    int moneyIndex = input.nextInt();
                    while (moneyIndex < 1 || moneyIndex >= index){
                        System.out.println("Invalid money index. Please choose again.");
                        moneyIndex = input.nextInt();
                    }
                    int money = map[moneyIndex];
                    bank.take(money);
                    paid += Bank.MONIES[money].getWorth();
                    break;
                }
                case 2:{
                    System.out.println("Which card will you pay with?");
                    int index = 1;
                    int[] map = new int[CardList.CARDS.length];
                    for (int i = 0; i < CardList.CARDS.length; i++) {
                        if (cardList.getNumOf(i) > 0){
                            map[index] = i;
                            Card card = CardList.CARDS[i];
                            System.out.printf(itemFormat, index++, card.getName(), cardList.getNumOf(i), card.getWorth());
                        }
                    }
                    int cardIndex = input.nextInt();
                    while (cardIndex < 1 || cardIndex >= index){
                        System.out.println("Invalid card index. Please choose again.");
                        cardIndex = input.nextInt();
                    }
                    int card = map[cardIndex];
                    cardList.take(card);
                    paid += CardList.CARDS[card].getWorth();
                    break;
                }
            }
        }

    }
}
