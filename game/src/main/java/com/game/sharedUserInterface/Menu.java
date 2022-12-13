package com.game.sharedUserInterface;

import com.game.Command.CommandExecutor;
import com.game.Command.ShowLeaderboardCommand;
import com.game.Command.SelectModeCommand;
import com.game.controllers.Player;
import com.game.entities.Entity;
import com.game.gamemode.GameMode;

import java.util.Scanner;

public class Menu {
    boolean isRunning = true;
    private static final CommandExecutor commandExecutor = new CommandExecutor();
    private Entity player;

    public Menu(Entity player) {
        this.player = player;
    }

    public void printMenu() {
        LocalMessages.displayVerticalLine();
        System.out.println("1. Singleplayer");
        System.out.println("2. Multiplayer");
        System.out.println("3. Leaderboard");
        System.out.println("4. Select character class:");
        System.out.println("5. Exit");
    }

    public void printMultiplayerMenu() {
        LocalMessages.displayVerticalLine();
        System.out.println("1. Join game");
        System.out.println("2. Host game");
        System.out.println("3. Back to main menu");
    }

    public void run() {
        Scanner inputReader = new Scanner(System.in);
        String command;
        printMenu();
        while(isRunning) {
            command = inputReader.nextLine();
            switch (command) {
                case "1":
                    SelectModeCommand selectSingleModeCommand = new SelectModeCommand(GameMode.SINGLEPLAYER);
                    commandExecutor.executeCommand(selectSingleModeCommand);
                    break;
                case "2":
                    SelectModeCommand selectMultiModeCommand = new SelectModeCommand(GameMode.MULTIPLAYER);
                    commandExecutor.executeCommand(selectMultiModeCommand);
                    break;
                case "3":
                    commandExecutor.executeCommand(new ShowLeaderboardCommand((Player) player.getController()));
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
