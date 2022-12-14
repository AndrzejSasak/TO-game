package com.game.Command;

import com.game.entities.Entity;
import com.game.gamemode.GameMode;
import com.game.levels.Training;

public class SelectModeCommand implements ICommand {

    private GameMode gameMode;
    private Entity player;

    public SelectModeCommand(GameMode gameMode, Entity player) {
        this.gameMode = gameMode;
        this.player = player;
    }

    @Override
    public void execute() {
        switch (gameMode) {
            case SINGLEPLAYER:
                Training training = new Training(player.getName());
                training.run();
                break;
            case MULTIPLAYER:
                //TODO: run multi player
                break;
        }
    }
}
