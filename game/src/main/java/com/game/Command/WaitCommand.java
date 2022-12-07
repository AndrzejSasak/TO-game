package com.game.Command;

public class WaitCommand implements Command{
    private Object player;

    WaitCommand(Object player) {
        this.player = player;
    }

    @Override
    public void execute() {
        // wait logic (raise critic to 100%)
    }
}
