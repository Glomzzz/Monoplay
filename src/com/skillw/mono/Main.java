package com.skillw.mono;

import com.skillw.mono.card.Card;
import com.skillw.mono.card.PerformableCard;
import com.skillw.mono.card.Property;
import com.skillw.mono.command.*;
import com.skillw.mono.game.GameState;
import com.skillw.mono.game.Player;
import com.skillw.mono.game.Properties;
import com.skillw.mono.game.PropertyList;

public class Main {
    public static void main(String[] args) {

        Interactor interactor = new Interactor();
        interactor.displayHeader();
        String[] names = interactor.registerPlayers();
        GameState game = new GameState(names);
        game.init();
        interactor.init(game);


        while (true){
            Player player = game.getCurrentPlayer();
            int actionRemain = 3;
            interactor.emptyGap();
            // Player draws 2 cards at the beginning of his/her turn
            for (int i = 0; i < 2; i++) {
                player.getCardList().draw();
            }
            System.out.println("======================================");
            while (actionRemain > 0){
                System.out.println();
                System.out.println(player.getName()+", this is your turn!");
                System.out.println("You can perform " + actionRemain +  " actions now:");
                System.out.println("1. Play an action card");
                System.out.println("2. Set a property");
                System.out.println("3. Collect Rent");
                System.out.println("4. Store Money");
                System.out.println("5. View cards on the table (not count as an action)");
                System.out.println("6. End your turn");
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
                        interactor.displayCards();
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
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
                            System.out.println("REPORT: Successfully drawed "+drawCards.getAmount()+" cards");
                        }
                            break;
                        case Command.SET_PROPERTY:
                        {
                            BuildProperty buildProperty = (BuildProperty) command;
                            PropertyList propertyList = player.getPropertyList();
                            Property property = buildProperty.getProperty();
                            if (property.getColors().length == 1){
                                propertyList.addProperty(property, property.getColors()[0]);
                            }
                            else {
                                Color chosen = interactor.chooseColor(player, property.getColors());
                                propertyList.addProperty(property, chosen);
                            }
                            System.out.println("Property successfully added on the table");
                        }
                            break;
                        case Command.SWAP_PROPERTY:
                        {
                            Player targetPlayer = interactor.selectPlayer(player, game);
                            Property self = interactor.selectSinglePropertyFrom(player, Interactor.INCOMPLETE);
                            //Check if the current player have property on the table that is incomplete
                            if (self == null) {
                                System.out.println("You don't have any property to swap.");
                                success = false;
                                break;
                            }
                            Property target = interactor.selectSinglePropertyFrom(targetPlayer, Interactor.INCOMPLETE);
                            //Check if the target player have property on the table that is incomplete
                            if (target == null) {
                                System.out.println(targetPlayer.getName() + " doesn't have any property to swap.");
                                success = false;
                                break;
                            }
                            {
                                Color chosen = interactor.chooseColor(targetPlayer, self.getColors());
                                targetPlayer.getPropertyList().addProperty(self, chosen);
                            }
                            {
                                Color chosen = interactor.chooseColor(player, target.getColors());
                                player.getPropertyList().addProperty(target, chosen);
                            }
                        }
                            break;
                        case Command.TAKE_PROPERTY:
                        {
                            Player targetPlayer = interactor.selectPlayer(player, game);
                            Property target = interactor.selectSinglePropertyFrom(targetPlayer, Interactor.INCOMPLETE);
                            Color[] colors = target.getColors();
                            if (colors.length == 1){
                                player.getPropertyList().addProperty(target, colors[0]);
                            }
                            else {
                                Color chosen = interactor.chooseColor(player, colors);
                                player.getPropertyList().addProperty(target, chosen);
                            }

                        }
                            break;
                        case Command.TAKE_COMPLETE_PROPERTY:
                        {
                            Player targetPlayer = interactor.selectPlayer(player, game);
                            Properties target = interactor.selectProperties(targetPlayer, Interactor.COMPLETED);

                            if (target == null){
                                System.out.println(targetPlayer.getName() + " doesn't have any completed property set."
                                + "\nAn action will be deducted as a punishment for not paying attention!");
                                break;
                            }
                            for (int i = 0; i < target.getData().length; i++) {
                                Property property = target.getData()[i];
                                Color[] colors = property.getColors();
                                if (colors.length == 1){
                                    if(!player.getPropertyList().addProperty(property, colors[0])){
                                        System.out.println("You have already owned a complete set of"+ colors[0].getName() +", you can't take this property card.");
                                        game.getCardStack().add(property);
                                    }
                                } else {
                                    Color chosen = interactor.chooseColor(player, colors);
                                    player.getPropertyList().addProperty(property, chosen);
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
                            System.out.println("REPORT: Successfully deposited $"+depositInBank.getMoney().getName());
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
                            System.out.println("Invalid command!");
                    }
                    if (success){
                        player.getCardList().consume(card);
                        actionRemain--;
                    }
                }
            }
            // If the player has more than 5 cards, he/she has to discard cards
            int needToDiscard = player.getCardList().size() - 5;
            if (needToDiscard > 0){
                System.out.println("You have more than 5 cards, you have to discard " + needToDiscard + " cards.");
                while (needToDiscard > 0){
                    System.out.println("Which card do you want to discard?   ( " + needToDiscard + " cards left )");
                    Card card = interactor.selectAllCard(player,false);
                    player.getCardList().consume(card);
                    needToDiscard--;
                }
            }
            game.nextTurn();
        }
    }
}