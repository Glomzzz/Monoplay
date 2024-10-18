package com.skillw.mono;

/**
 *
 * Kevin, this is your turn!
 * Kevin, You can perform 3 actions now:
 * 1. Perform an action
 * 2. Set a property
 * 3. Collect Rent
 * 4. Store Money
 * 5. View other player's card on the table
 * 6. End your turn
 * Your choice: 1
 * Here are the cards you can perform:
 * 0. Exit
 * 1. Birthday [1]
 * 2. Debt Collector [1]
 *
 * Your choice: 2
 * Who do you want to perform the card on?
 * 1. Morro
 * 2. Glom
 * Your choice: 2
 * You have chosen Glom.
 * Glom, you have to pay $ 5 M to Kevin.
 * Please select the cards you want to pay with (reamining $ 5 M):
 * 1. $ 2 M     x 2             - $ 2 M
 * 1
 * Please select the cards you want to pay with (reamining $ 3 M):
 * 1.$ 2 M      x 1            - $ 2 M
 * 1
 * Please select the cards you want to pay with (reamining $ 2 M):
 * 1. Property Red      x 1     - $ 2 M
 * 1
 * Glom has paid $ 5 M to Kevin.
 *
 * Kevin, You can perform 3 cards now:
 *  * 1. Perform an action
 *  * 2. Set a property
 *  * 3. Rent
 *  * 4. Store Money
 *  * 5. End your turn
 *  Your choice: .........
 */

public class Main {
    public static void main(String[] args) {
        Display display = new Display();
        display.DisplayHeader();
        System.out.println();
        System.out.println("How many players are there?");
    }
}