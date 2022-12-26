package com.game.Command.multiplayerMenuCommand;

import com.game.entities.Entity;
import com.game.multiplayer.Server;

import java.io.IOException;

public class HostCommand implements IMenuCommand{
    Entity player;

    public HostCommand(Entity player) {
        this.player = player;
    }

    @Override
    public void execute() {
        Server server = Server.getInstance();
        if(server == null){
            System.out.println("Cannot create server! Backing to main menu!");
            return;
        }
        server.Setup(player);
    }
}
