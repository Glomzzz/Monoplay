package com.skillw.mono;

import com.skillw.mono.card.Card;
import com.skillw.mono.card.PerformableCard;
import com.skillw.mono.card.Property;
import com.skillw.mono.command.*;
import com.skillw.mono.game.GameState;
import com.skillw.mono.game.Player;
import com.skillw.mono.game.Properties;

public class Main {
    public static void main(String[] args) {
        Interactor interactor = new Interactor();
        GameState game;
        interactor.displayHeader();
        while (true){
            String[] names = interactor.registerPlayers();
            game = new GameState(names);
            game.init();
            interactor.init(game);

            while (!game.hasWinner()){
                Player player = game.getCurrentPlayer();
                interactor.setCurrentPlayer(player);
                int actionRemain = 3;
                interactor.emptyGap();
                // Player draws 2 cards at the beginning of his/her turn
                for (int i = 0; i < 2; i++) {
                    player.getCardList().draw();
                }
                interactor.println("======================================");
                while (actionRemain > 0 && !game.hasWinner()){
                    interactor.println("");
                    interactor.println(player.getName()+", this is your turn!");
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
                            card = interactor.selectActionCard(player,true);
                            break;
                        case 2:
                            card = interactor.selectPropertyCard(player,true);
                            break;
                        case 3:
                            card = interactor.selectRentCard(player,true);
                            break;
                        case 4:
                            Card selected = interactor.selectAllCard(player,true);
                            if (selected != null) card = selected.asMoney();
                            break;
                        case 5:
                            interactor.displayState();
                            break;
                        default:
                            interactor.println("Invalid choice, please try again.");
                    }
                    if (card != null){
                        Command command = card.action(game,player);
                        boolean success = true;
                        switch (command.getId()){
                            case Command.PAY_MONEY:
                            {
                                PayMoney payMoney = (PayMoney) command;
                                Player target = interactor.selectPlayer(player, game);
                                interactor.askToPay(target, player, payMoney.getAmount());
                                break;
                            }
                            case Command.DRAW_CARDS:
                            {
                                DrawCards drawCards = (DrawCards) command;
                                for (int i = 0; i < drawCards.getAmount(); i++) {
                                    player.getCardList().draw();
                                }
                                interactor.alert("Successfully drawed "+drawCards.getAmount()+" cards");
                            }
                            break;
                            case Command.SET_PROPERTY:
                            {
                                BuildProperty buildProperty = (BuildProperty) command;
                                Property property = buildProperty.getProperty();
                                interactor.setProperty(player,property);
                                interactor.alert("Property successfully added on the table");
                            }
                            break;
                            case Command.SWAP_PROPERTY:
                            {
                                Player targetPlayer = interactor.selectPlayer(player, game);
                                Property self = interactor.selectSinglePropertyFrom(player,player, Interactor.INCOMPLETE);
                                //Check if the current player have property on the table that is incomplete
                                if (self == null) {
                                    interactor.alert("You don't have any property to swap.");
                                    success = false;
                                    break;
                                }
                                Property target = interactor.selectSinglePropertyFrom(targetPlayer,player, Interactor.INCOMPLETE);
                                //Check if the target player have property on the table that is incomplete
                                if (target == null) {
                                    interactor.alert(targetPlayer.getName() + " doesn't have any property to swap.");
                                    success = false;
                                    break;
                                }
                                interactor.setProperty(targetPlayer, self);
                                interactor.setProperty(player, target);
                            }
                            break;
                            case Command.TAKE_PROPERTY:
                            {
                                Player targetPlayer = interactor.selectPlayer(player, game);
                                Property target = interactor.selectSinglePropertyFrom(targetPlayer,player, Interactor.INCOMPLETE);
                                interactor.setProperty(player, target);
                            }
                            break;
                            case Command.TAKE_COMPLETE_PROPERTY:
                            {
                                Player targetPlayer = interactor.selectPlayer(player, game);
                                Properties target = interactor.selectProperties(targetPlayer,player, Interactor.COMPLETED);

                                if (target == null){
                                    interactor.alert(targetPlayer.getName() + " doesn't have any completed property set."
                                            + "\nAn action will be deducted as a punishment for not paying attention!");
                                    break;
                                }
                                for (int i = 0; i < target.getData().length; i++) {
                                    Property property = target.getData()[i];
                                    Color[] colors = property.getColors();
                                    if (colors.length == 1){
                                        if(!player.getPropertyList().addProperty(property, colors[0])){
                                            interactor.alert("You have already owned a complete set of"+ colors[0].getName() +", you can't take this property card.");
                                            game.getCardStack().add(property);
                                        }
                                    } else {
                                        interactor.setProperty(player, property);
                                    }
                                }
                            }
                            break;
                            case Command.RENT:
                            {
                                RentSingleColor rentSingleColor = (RentSingleColor) command;
                                Color color = interactor.chooseColor(player,rentSingleColor.getColors());
                                Player target = interactor.selectPlayer(player, game);
                                int amount = player.getPropertyList().getProperties(color).calculateWorth();
                                interactor.askToPay(target, player, amount);
                            }
                            break;
                            case Command.RENT_UNIVERSAL:
                            {
                                RentAllColor rentAllColor = (RentAllColor) command;
                                Color color = interactor.chooseColor(player,rentAllColor.getColors());
                                Player target = interactor.selectPlayer(player, game);
                                int amount = player.getPropertyList().getProperties(color).calculateWorth();
                                interactor.askToPay(target, player, amount);
                            }
                            break;
                            case Command.DEPOSIT_IN_BANK:
                            {
                                DepositInBank depositInBank = (DepositInBank) command;
                                interactor.depositMoney(player, depositInBank.getMoney());
                                interactor.alert("REPORT: Successfully deposited $"+depositInBank.getMoney().getName());
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
                            player.getCardList().consume(card);
                            actionRemain--;
                        }
                    }
                }
                if (game.hasWinner()) break;
                // If the player has more than 5 cards, he/she has to discard cards
                int needToDiscard = player.getCardList().size() - 5;
                if (needToDiscard > 0){
                    interactor.println("You have more than 5 cards, you have to discard " + needToDiscard + " cards.");
                    while (needToDiscard > 0){
                        interactor.println("Which card do you want to discard?   ( " + needToDiscard + " cards left )");
                        Card card = interactor.selectAllCard(player,false);
                        player.getCardList().consume(card);
                        needToDiscard--;
                    }
                }
                game.nextTurn();
            }
            Player winner = game.getWinner();
            interactor.alert("Congratulations! " + winner.getName() + " has won the game!");
        }
    }
}