package com.game.menu;

import com.game.Command.CommandExecutor;
import com.game.entities.Entity;

public abstract class IMenu {
    boolean isRunning;
    protected CommandExecutor commandExecutor;
    protected Entity player;

    IMenu() {
        commandExecutor = new CommandExecutor();
        isRunning = true;
    }

    protected abstract void printMenu();
    public abstract void run();

}
