package com.game.Command.multiplayerMenuCommand;

import com.game.entities.Entity;
import com.game.multiplayer.Client;
import com.game.multiplayer.IOManager;

import java.io.IOException;
import java.net.Socket;

public class JoinCommand implements IMenuCommand{
    Entity player;

    public JoinCommand(Entity player) {
        this.player = player;
    }

    @Override
    public void execute() {
        try {
            Socket clientSocket = new Socket("localhost", 5000);
            IOManager ioManager = new IOManager(clientSocket);
            Client newClient = new Client(clientSocket, ioManager, this.player);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
