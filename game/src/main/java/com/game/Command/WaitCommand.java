package com.game.Command;

public class WaitCommand implements ICommand {
    private Object player;

    WaitCommand(Object player) {
        this.player = player;
    }

    @Override
    public void execute() {
        //TODO: wait logic (raise critic to 100%)
    }
}