package com.game.menu;

import com.game.Command.CommandExecutor;
import com.game.Command.ShowLeaderboardCommand;
import com.game.Command.SelectModeCommand;
import com.game.controllers.PlayerEntityController;
import com.game.entities.Entity;
import com.game.gamemode.GameMode;
import com.game.sharedUserInterface.LocalMessages;
import com.game.sharedUserInterface.SelectEntityType;

import java.util.Scanner;

public class MainMenu extends IMenu{
    public MainMenu(Entity player) {
        super();
        this.player = player;
    }

    @Override
    protected void printMenu() {
        LocalMessages.displayVerticalLine();
        System.out.println("1. Singleplayer");
        System.out.println("2. Multiplayer");
        System.out.println("3. Leaderboard");
        System.out.println("4. Select character class");
        System.out.println("5. Exit");
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
                    SelectModeCommand selectSingleModeCommand = new SelectModeCommand(GameMode.SINGLEPLAYER, player);
                    commandExecutor.executeCommand(selectSingleModeCommand);
                    break;
                case "2":
                    SelectModeCommand selectMultiModeCommand = new SelectModeCommand(GameMode.MULTIPLAYER, player);
                    commandExecutor.executeCommand(selectMultiModeCommand);
                    break;
                case "3":
                    commandExecutor.executeCommand(new ShowLeaderboardCommand(player));
                    break;
                case "4":
                    player = new SelectEntityType().updateEntity(player);
                    break;
                case "5":
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
}
