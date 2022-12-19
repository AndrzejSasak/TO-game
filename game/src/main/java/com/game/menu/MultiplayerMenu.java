package com.game.menu;

import com.game.Command.CommandExecutor;
import com.game.Command.SelectModeCommand;
import com.game.Command.ShowLeaderboardCommand;
import com.game.Command.multiplayerMenuCommand.HostCommand;
import com.game.Command.multiplayerMenuCommand.JoinCommand;
import com.game.controllers.PlayerEntityController;
import com.game.controllers.RemotePlayerEntityController;
import com.game.entities.Entity;
import com.game.gamemode.GameMode;
import com.game.sharedUserInterface.LocalMessages;

import javax.print.attribute.standard.JobName;
import java.util.Scanner;

public class MultiplayerMenu extends IMenu{

    public MultiplayerMenu(Entity player) {
        super();
        this.player = player;
        PlayerEntityController playerEntityController = (PlayerEntityController) player.getController();
        this.player.setController(new RemotePlayerEntityController(playerEntityController.getEntityOwner()));
    }

    @Override
    public void run() {
        Scanner inputReader = new Scanner(System.in);
        String command;
        printMenu();
        while(isRunning) {
            command = inputReader.nextLine();
            switch (command) {
                case "1":
                    JoinCommand joinCommand = new JoinCommand(player);
                    commandExecutor.executeCommand(joinCommand);
                    break;
                case "2":
                    HostCommand hostCommand = new HostCommand(player);
                    commandExecutor.executeCommand(hostCommand);
                    break;
                case "3":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Bad command!");
                    break;
            }

            if(isRunning) {
                printMenu();
            }
        }
    }

    @Override
    protected void printMenu() {
        LocalMessages.displayVerticalLine();
        System.out.println("1. Join game");
        System.out.println("2. Host game");
        System.out.println("3. Back to main menu");
    }

}
