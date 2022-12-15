package com.game.Command.multiplayerMenuCommand;

import com.game.entities.Entity;

public class HostCommand implements IMenuCommand{
    Entity player;

    public HostCommand(Entity player) {
        this.player = player;
    }

    @Override
    public void execute() {
        //TODO: run server
    }
}
