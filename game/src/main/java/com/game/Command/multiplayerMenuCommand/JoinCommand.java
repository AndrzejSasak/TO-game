package com.game.Command.multiplayerMenuCommand;

import com.game.entities.Entity;

public class JoinCommand implements IMenuCommand{
    Entity player;

    public JoinCommand(Entity player) {
        this.player = player;
    }
    @Override
    public void execute() {
        //TODO: start client
    }
}
