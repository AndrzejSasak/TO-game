package com.game.menu;

import com.game.Command.CommandExecutor;
import com.game.Command.LeaderboardCommand;
import com.game.Command.SelectModeCommand;
import com.game.controllers.Player;
import com.game.gamemode.GameMode;

import java.util.Scanner;

public class Menu {
    static boolean isRunning = true;
    private static final CommandExecutor commandExecutor = new CommandExecutor();
    private Player player;

    public Menu(Player player) {
        this.player = player;
    }

    public void printMenu() {
        System.out.println("1. Singleplayer");
        System.out.println("2. Multiplayer");
        System.out.println("3. Leader");
        System.out.println("4. Exit");
    }

    public void printMultiplayerMenu() {
        System.out.println("1. Join game");
        System.out.println("2. Host game");
        System.out.println("3. Back to main menu");
    }

    public void run() {
        Scanner inputReader = new Scanner(System.in);
        String command;
        printMenu();
        while(Menu.isRunning) {
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
                    commandExecutor.executeCommand(new LeaderboardCommand(player));
                    break;
                case "4":
                    Menu.isRunning = false;
                    break;
                default:
                    System.out.println("Bad command!");
                    printMenu();
                    break;
            }
        }
    }
}
