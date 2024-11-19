package com.skillw.mono;

import com.skillw.mono.card.Card;
import com.skillw.mono.card.PerformableCard;
import com.skillw.mono.card.Property;
import com.skillw.mono.command.*;
import com.skillw.mono.game.GameState;
import com.skillw.mono.game.Player;
import com.skillw.mono.game.Properties;

import static com.skillw.mono.Interactor.COLOR_FILTER_OWNED;

public class Main {
    /**
     * The main method of the game
     *
     * @param args The arguments of the main method
     */
    public static void main(String[] args) {

        Interactor interactor = new Interactor();
        GameState game;

        interactor.displayHeader(); //Display the title of the game
        String[] names = interactor.registerPlayers(); //Add players
        game = new GameState(names);
        game.init();
        interactor.init(game);

        /*
         * Draw 3 cards for each player at the beginning
         * (+2 will be added when it's their turn)
         */
        Player[] allPlayers = game.getAllPlayers();
        for (int i = 0; i < allPlayers.length; i++) {
            for (int j = 0; j < 3; j++) {
              interactor.drawCard( allPlayers[i]);
            }
        }
        // A variable to decrease times to check if there is a winner
        boolean hasWinner = false;
        // The main loop of the game
        while (true) {
            Player currPlayer = game.getCurrentPlayer();
            interactor.setPrefixPlayer(currPlayer); //set current player

            // The number of actions the player can perform in a turn
            int actionRemain = 3;
            interactor.emptyGap();
            interactor.println("======================================");
            // Player draws 2 cards at the beginning of his/her turn
            for (int i = 0; i < 2; i++) {
                interactor.drawCard(currPlayer);
            }
            // Check if there is a winner
            while (actionRemain > 0 && !hasWinner){
                interactor.println("======================================");
                interactor.setPrefixPlayer(currPlayer);
                interactor.println("");
                interactor.println(currPlayer.getName()+", this is your turn!");
                interactor.displaySpecialCardNum(currPlayer);
                interactor.println("You can perform " + actionRemain +  " actions now:");
                interactor.println("1. Play an action card");
                interactor.println("2. Set a property");
                interactor.println("3. Collect Rent");
                interactor.println("4. Store Money");
                interactor.println("5. View cards on the table (not count as an action)");
                interactor.println("6. End your turn");
                int choice = interactor.readInt("Your choice: ");

                if (choice == 6) break;
                // Select a card to perform
                PerformableCard card = null;
                switch (choice){
                    case 1: //Display action card(s)
                        card = interactor.selectActionCard(currPlayer,true);
                        break;
                    case 2: //Display property card(s)
                        card = interactor.selectPropertyCard(currPlayer,true);
                        break;
                    case 3: //Display rent card(s)
                        card = interactor.selectRentCard(currPlayer,true);
                        break;
                    case 4: //Display all card(s) since all of them can be money
                        Card selected = interactor.selectAllCard(currPlayer,true);
                        if (selected != null) card = selected.asMoney();
                        break;
                    case 5: //Display the cards on the table
                        interactor.displayState();
                        break;
                    default:
                        interactor.println("Invalid choice, please try again.");
                }
                // Perform the action if a card is selected
                if (card != null){
                    Command command = card.action(currPlayer);
                    // is the command being performed successfully
                    boolean success = true;
                    switch (command.getId()){
                        case Command.DRAW_CARDS:
                        {
                            DrawCards drawCards = (DrawCards) command;
                            for (int i = 0; i < drawCards.getAmount(); i++) {
                                interactor.drawCard(currPlayer);
                            }
                        }
                        break;
                        case Command.SET_PROPERTY:
                        {
                            BuildProperty buildProperty = (BuildProperty) command;
                            Property property = buildProperty.getProperty();
                            success = interactor.setPropertyOrSave(currPlayer,property);
                        }
                        break;
                        case Command.DEPOSIT_IN_BANK:
                        {
                            DepositInBank depositInBank = (DepositInBank) command;
                            interactor.depositMoney(currPlayer, depositInBank.getMoney());
                        }
                        break;
                        case Command.PAY_MONEY:
                        {
                            PayMoney payMoney = (PayMoney) command;
                            Player target = interactor.selectPlayer(currPlayer, game);
                            // Ask the target if he/she wants to say No if they have No Card
                            if (interactor.refuseByNo(target,"pay "+ interactor.moneyFormat(payMoney.getAmount())+ " to " + currPlayer.getName())) break;
                            interactor.askToPay(target, currPlayer, payMoney.getAmount());
                            break;
                        }
                        case Command.ALL_PAY_MONEY:
                        {
                            AllPayMoney payMoney = (AllPayMoney) command;
                            int amount = payMoney.getAmount();
                            for (int i = 0; i < allPlayers.length; i++) {
                                Player player = allPlayers[i];
                                // Ask the target if he/she wants to say No if they have No Card
                                boolean refused = interactor.refuseByNo(player,"pay "+ interactor.moneyFormat(payMoney.getAmount()) + " to " + currPlayer.getName());
                                if (player != currPlayer && !refused) {
                                    interactor.askToPay(player, currPlayer, amount);
                                }
                            }
                        }
                        break;
                        case Command.SWAP_PROPERTY:
                        {
                            Player targetPlayer = interactor.selectPlayer(currPlayer, game);
                            // Ask the target if he wants to say No
                            if (interactor.refuseByNo(targetPlayer,"swap your property with " + currPlayer.getName() + "'s ")) break;
                            Property self = interactor.selectSinglePropertyFrom(currPlayer,currPlayer, Interactor.PROPERTIES_FILTER_INCOMPLETE);
                            //Check if the current currPlayer have property on the table that is incomplete
                            if (self == null) {
                                interactor.alert("You don't have any property to swap.");
                                // If the current player doesn't have any property to swap, the action is not performed
                                // The action remain will not be decreased
                                success = false;
                                break;
                            }
                            Property target = interactor.selectSinglePropertyFrom(targetPlayer,currPlayer, Interactor.PROPERTIES_FILTER_INCOMPLETE);
                            //Check if the target currPlayer have property on the table that is incomplete
                            if (target == null) {
                                interactor.alert(targetPlayer.getName() + " doesn't have any property to swap.");
                                // If the target player doesn't have any property to swap, the action is not performed
                                // The action remain will not be decreased
                                success = false;
                                break;
                            }
                            interactor.setPropertyOrDrop(targetPlayer, self);
                            interactor.setPropertyOrDrop(currPlayer, target);
                        }
                        break;
                        case Command.TAKE_PROPERTY:
                        {
                            Player targetPlayer = interactor.selectPlayer(currPlayer, game);
                            // Ask the target if he/she wants to say No if they have No Card
                            if (interactor.refuseByNo(targetPlayer,"give your property to " + currPlayer.getName())) break;
                            Property target = interactor.selectSinglePropertyFrom(targetPlayer,currPlayer, Interactor.PROPERTIES_FILTER_INCOMPLETE);
                            if (target == null){
                                interactor.println(targetPlayer.getName() + " doesn't have any property to give !");
                                interactor.alert("An action will be deducted as a punishment for not paying attention!");
                                break;
                            }
                            interactor.setPropertyOrDrop(currPlayer, target);
                        }
                        break;
                        case Command.TAKE_COMPLETE_PROPERTY:
                        {
                            Player targetPlayer = interactor.selectPlayer(currPlayer, game);
                            // Ask the target if he/she wants to say No if they have No Card
                            if (interactor.refuseByNo(targetPlayer,"give your complete set to " + currPlayer.getName())) break;
                            Properties target = interactor.selectProperties(targetPlayer,currPlayer, Interactor.PROPERTIES_FILTER_COMPLETED);
                            if (target == null){
                                interactor.println(targetPlayer.getName() + " doesn't have any complete set to give !");
                                interactor.alert("An action will be deducted as a punishment for not paying attention!");
                                break;
                            }
                            // Remove the color from the target player's property list
                            targetPlayer.getPropertyList().clearColor(target.getColor());
                            // Add the properties to the current player's property list
                            for (int i = 0; i < target.getData().length; i++) {
                                Property property = target.getData()[i];
                                // Set the property to the current player,
                                // if the current player already has all complete set of each of this property colors, drop it
                                interactor.setPropertyOrDrop(currPlayer, property);
                            }
                        }
                        break;
                        case Command.RENT:
                        {
                            RentSingleColor rentSingleColor = (RentSingleColor) command;
                            Color color = interactor.chooseColor(currPlayer,rentSingleColor.getColors(),COLOR_FILTER_OWNED);
                            if (color == null){
                                interactor.alert("You didn't choose any color.");
                                // If the player didn't choose any color, the action is not performed
                                // The action remain will not be decreased
                                success = false;
                                break;
                            }
                            int amount = currPlayer.getPropertyList().getProperties(color).calculateWorth();
                            // Ask the player if he wants to double the rent
                            if (actionRemain > 1 && interactor.doubleTheRent(currPlayer)) {
                                amount *= 2;
                                // Double The Rent also counted as an action
                                actionRemain--;
                            }
                            Player performer = rentSingleColor.getPerformer();
                            for (int i = 0; i < allPlayers.length; i++) {
                                Player target = allPlayers[i];
                                // Ask the target if he wants to say No
                                boolean refused = interactor.refuseByNo(target,"pay "+ interactor.moneyFormat(amount)+ " to " + currPlayer.getName());
                                if (target != performer && !refused) {
                                    interactor.askToPay(target, performer, amount);
                                }
                            }
                        }
                        break;
                        case Command.RENT_UNIVERSAL:
                        {
                            RentAllColor rentAllColor = (RentAllColor) command;
                            Color color = interactor.chooseColor(currPlayer,rentAllColor.getColors(),COLOR_FILTER_OWNED);
                            if (color == null){
                                interactor.alert("You didn't choose any color.");
                                // If the player didn't choose any color, the action is not performed
                                // The action remain will not be decreased
                                success = false;
                                break;
                            }
                            Player target = interactor.selectPlayer(currPlayer, game);
                            int amount = currPlayer.getPropertyList().getProperties(color).calculateWorth();
                            // Ask the player if he wants to double the rent
                            if (actionRemain > 1 && interactor.doubleTheRent(currPlayer)) {
                                amount *= 2;
                                // Double The Rent also counted as an action
                                actionRemain--;
                            }
                            // Ask the target if he wants to say No
                            if (interactor.refuseByNo(target,"pay "+ interactor.moneyFormat(amount)+ " to " + currPlayer.getName())) break;
                            interactor.askToPay(target, currPlayer, amount);
                        }
                        break;
                        default:
                            interactor.alert("Invalid command!");
                    }
                    // If the command is performed successfully, decrease the action remain and consume the card.
                    if (success){
                        currPlayer.getCardList().consume(card);
                        actionRemain--;
                    }
                }
                // Check if there is a winner
                hasWinner = game.hasWinner();
            }
            if (hasWinner) break;

            // If the current player has more than 5 cards, he/she has to discard cards
            int discard = currPlayer.getCardList().getSize() - 5;
            if (discard > 0){
                interactor.println("You have more than 5 cards, you have to discard " + discard + " cards.");
                while (discard > 0){
                    interactor.println("Which card do you want to discard?   ( " + discard + " cards left )");
                    Card card = interactor.selectAllCard(currPlayer,false);
                    currPlayer.getCardList().consume(card);
                    discard--;
                }
            }
            interactor.alert("End of turn for " + currPlayer.getName());
            game.nextTurn(); //Next turn
        }
        Player winner = game.getWinner();
        interactor.alert("THE WINNER ISSS " + winner.getName() + "!!!! XDD");
        interactor.displayState();
        interactor.exit();
    }
}