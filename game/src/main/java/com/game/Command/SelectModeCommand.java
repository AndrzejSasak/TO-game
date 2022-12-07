package com.game.Command;

public class SelectModeCommand implements ICommand {

    private Object gameMode;

    SelectModeCommand(Object gameMode) {
        this.gameMode = gameMode;
    }

    @Override
    public void execute() {
        //TODO: run single player game or multiplayer menu
    }
}
