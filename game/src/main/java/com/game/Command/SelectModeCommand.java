package com.game.Command;

import com.game.entities.Entity;
import com.game.gamemode.GameMode;
import com.game.levels.Training;
import com.game.menu.IMenu;
import com.game.menu.MultiplayerMenuCreator;

public class SelectModeCommand implements ICommand {

    private final GameMode gameMode;
    private final Entity player;

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
                IMenu multiplayerMenu = new MultiplayerMenuCreator().createMenu(player);
                multiplayerMenu.run();
                break;
        }
    }
}
