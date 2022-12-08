package com.game.Command;

import com.game.gamemode.GameMode;

public class SelectModeCommand implements ICommand {

    private GameMode gameMode;

    SelectModeCommand(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    @Override
    public void execute() {
        //TODO: run single player game or multiplayer menu
    }
}
