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
    public static void main(String[] args) {

        Interactor interactor = new Interactor();
        GameState game;

        interactor.displayHeader(); //Display the title of the game
        String[] names = interactor.registerPlayers();
        game = new GameState(names);
        game.init();
        interactor.init(game);

        while (!game.hasWinner()){
            Player currPlayer = game.getCurrentPlayer();
            interactor.setCurrentPlayer(currPlayer);
            int actionRemain = 3;
            interactor.emptyGap();

            interactor.println("======================================");
            // Player draws 2 cards at the beginning of his/her turn
            for (int i = 0; i < 2; i++) {
                interactor.drawCard(currPlayer);
            }
            boolean hasWinner = game.hasWinner();
            while (actionRemain > 0 && !hasWinner){
                interactor.println("======================================");
                interactor.setCurrentPlayer(currPlayer);
                interactor.println("");
                interactor.println(currPlayer.getName()+", this is your turn!");

                interactor.displayUniqueCardNum(currPlayer);
                interactor.println("You can perform " + actionRemain +  " actions now:");
                interactor.println("1. Play an action card");
                interactor.println("2. Set a property");
                interactor.println("3. Collect Rent");
                interactor.println("4. Store Money");
                interactor.println("5. View cards on the table (not count as an action)");
                interactor.println("6. End your turn");
                int choice = interactor.readInt("Your choice: ");

                if (choice == 6) break;
                PerformableCard card = null;
                switch (choice){
                    case 1:
                        card = interactor.selectActionCard(currPlayer,true);
                        break;
                    case 2:
                        card = interactor.selectPropertyCard(currPlayer,true);
                        break;
                    case 3:
                        card = interactor.selectRentCard(currPlayer,true);
                        break;
                    case 4:
                        Card selected = interactor.selectAllCard(currPlayer,true);
                        if (selected != null) card = selected.asMoney();
                        break;
                    case 5:
                        interactor.displayState();
                        break;
                    default:
                        interactor.println("Invalid choice, please try again.");
                }
                if (card != null){
                    Command command = card.action(game,currPlayer);
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
                            interactor.setProperty(currPlayer,property);
                            interactor.alert(property.getName()  +" successfully added on the table");
                        }
                        break;
                        case Command.PAY_MONEY:
                        {
                            PayMoney payMoney = (PayMoney) command;
                            Player target = interactor.selectPlayer(currPlayer, game);
                            if (interactor.refuseByNo(target,"pay $"+payMoney.getAmount() + " M to " + currPlayer.getName())) break;
                            interactor.askToPay(target, currPlayer, payMoney.getAmount());
                            break;
                        }
                        case Command.SWAP_PROPERTY:
                        {
                            Player targetPlayer = interactor.selectPlayer(currPlayer, game);
                            if (interactor.refuseByNo(targetPlayer,"swap your property with " + currPlayer.getName() + "'s ")) break;
                            Property self = interactor.selectSinglePropertyFrom(currPlayer,currPlayer, Interactor.INCOMPLETE);
                            //Check if the current currPlayer have property on the table that is incomplete
                            if (self == null) {
                                interactor.alert("You don't have any property to swap.");
                                success = false;
                                break;
                            }
                            Property target = interactor.selectSinglePropertyFrom(targetPlayer,currPlayer, Interactor.INCOMPLETE);
                            //Check if the target currPlayer have property on the table that is incomplete
                            if (target == null) {
                                interactor.alert(targetPlayer.getName() + " doesn't have any property to swap.");
                                success = false;
                                break;
                            }
                            interactor.setProperty(targetPlayer, self);
                            interactor.setProperty(currPlayer, target);
                        }
                        break;
                        case Command.TAKE_PROPERTY:
                        {
                            Player targetPlayer = interactor.selectPlayer(currPlayer, game);
                            if (interactor.refuseByNo(targetPlayer,"give your property to " + currPlayer.getName())) break;
                            Property target = interactor.selectSinglePropertyFrom(targetPlayer,currPlayer, Interactor.INCOMPLETE);
                            interactor.setProperty(currPlayer, target);
                        }
                        break;
                        case Command.TAKE_COMPLETE_PROPERTY:
                        {
                            Player targetPlayer = interactor.selectPlayer(currPlayer, game);
                            if (interactor.refuseByNo(targetPlayer,"give your complete set to " + currPlayer.getName())) break;
                            Properties target = interactor.selectProperties(targetPlayer,currPlayer, Interactor.COMPLETED);

                            if (target == null){
                                interactor.alert(targetPlayer.getName() + " doesn't have any completed property set."
                                        + "\nAn action will be deducted as a punishment for not paying attention!");
                                break;
                            }
                            for (int i = 0; i < target.getData().length; i++) {
                                Property property = target.getData()[i];
                                Color[] colors = property.getColors();
                                if (colors.length == 1){
                                    if(!currPlayer.getPropertyList().addProperty(property, colors[0])){
                                        interactor.alert("You have already completed the set for"+ colors[0].getName()+
                                                        ", you can't take this property card."+
                                                        "\n The card will be putted in the cards on the table");
                                        game.getCardStack().add(property);
                                    }
                                } else {
                                    interactor.setProperty(currPlayer, property);
                                }
                            }
                        }
                        break;
                        case Command.RENT:
                        {
                            RentSingleColor rentSingleColor = (RentSingleColor) command;
                            Color color = interactor.chooseColor(currPlayer,rentSingleColor.getColors(),COLOR_FILTER_OWNED);
                            if (color == null){
                                interactor.alert("You didn't choose any color.");
                                success = false;
                                break;
                            }
                            int amount = currPlayer.getPropertyList().getProperties(color).calculateTotalWorth();
                            if (interactor.doubleTheRent(currPlayer)) amount *= 2;
                            Player performer = rentSingleColor.getPerformer();
                            for (int i = 0; i < game.getAllPlayers().length; i++) {
                                Player target = game.getAllPlayers()[i];
                                if (target != performer && !interactor.refuseByNo(target,"pay $"+amount + " M to " + currPlayer.getName())) {
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
                                success = false;
                                break;
                            }
                            Player target = interactor.selectPlayer(currPlayer, game);
                            int amount = currPlayer.getPropertyList().getProperties(color).calculateTotalWorth();
                            if (interactor.doubleTheRent(currPlayer)) amount *= 2;
                            if (interactor.refuseByNo(target,"pay $"+amount + " M to " + currPlayer.getName())) break;
                            interactor.askToPay(target, currPlayer, amount);
                        }
                        break;
                        case Command.DEPOSIT_IN_BANK:
                        {
                            DepositInBank depositInBank = (DepositInBank) command;
                            interactor.depositMoney(currPlayer, depositInBank.getMoney());
                            interactor.alert("REPORT: Successfully deposited "+depositInBank.getMoney().getName());
                        }
                        break;
                        case Command.ALL_PAY_MONEY:
                        {
                            AllPayMoney payMoney = (AllPayMoney) command;
                            Player performer = payMoney.getPerformer();
                            int amount = payMoney.getAmount();
                            for (int i = 0; i < game.getAllPlayers().length; i++) {
                                if (game.getAllPlayers()[i] != performer) {
                                    interactor.askToPay(game.getAllPlayers()[i], performer, amount);
                                }
                            }
                        }
                        break;
                        default:
                            interactor.alert("Invalid command!");
                    }
                    if (success){
                        currPlayer.getCardList().consume(card);
                        actionRemain--;
                    }
                }
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
    }
}