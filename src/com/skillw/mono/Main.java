package com.skillw.mono;

import com.skillw.mono.card.Card;
import com.skillw.mono.card.PerformableCard;
import com.skillw.mono.command.Command;
import com.skillw.mono.game.GameState;
import com.skillw.mono.game.Player;

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
 * Kevin, You can perform 2 actions now:
 *  * 1. Perform an action
 *  * 2. Set a property
 *  * 3. Rent
 *  * 4. Store Money
 *  * 5. End your turn
 *  Your choice: .........
 */

public class Main {
    public static void main(String[] args) {
        Interactor interactor = new Interactor();

        interactor.displayHeader();
        String[] names = interactor.registerPlayers();

        GameState game = new GameState(names);
        game.init();
        while (true){
            Player player = game.getCurrentPlayer();
            int actionRemain = 3;
            System.out.println("======================================");
            while (actionRemain > 0){
                System.out.println(player.getName()+", this is your turn!");
                System.out.println("You can perform " + actionRemain +  " actions now:");
                System.out.println("1. Play an action card");
                System.out.println("2. Set a property");
                System.out.println("3. Collect Rent");
                System.out.println("4. Store Money");
                System.out.println("5. View other player's card on the table (not count as an action)");
                System.out.println("6. End your turn");
                System.out.print("Your choice: ");
                int choice = interactor.readInt();

                if (choice == 6) break;
                PerformableCard card = null;
                switch (choice){
                    case 1:
                        card = interactor.selectActionCard(player);
                        break;
                    case 2:
                        card = interactor.selectPropertyCard(player);
                        break;
                    case 3:
                        card = interactor.selectRentCard(player);
                        break;
                    case 4:
                        Card selected = interactor.selectAllCard(player);
                        player.getCardList().take(selected);
                        card = selected.asMoney();
                        break;
                    case 5:

                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
                if (card != null){
                    Command command = card.perform(game,player);
                    switch (command.getId()){
                        case Command.GO_BACK:
                            break;
                        case Command.PAY_MONEY:
                            break;
                        case Command.DRAW_CARDS:
                            break;
                        case Command.RENT:
                            break;
                        case Command.BUILD_PROPERTY:
                            break;
                        case Command.GIVE_PROPERTY:
                            break;
                        case Command.GIVE_COMPLETE_SET:
                            break;
                        case Command.RENT_UNIVERSAL:
                            break;
                        case Command.DESPOIT_IN_BANK:
                            break;
                        case Command.ALL_PAY_MONEY:
                            break;
                        default:
                            System.out.println("Invalid command!");
                    }
                    actionRemain--;
                }
            }
            game.nextTurn();
        }
    }
}