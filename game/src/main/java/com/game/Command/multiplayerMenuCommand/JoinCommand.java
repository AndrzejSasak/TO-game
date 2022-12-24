package com.game.Command.multiplayerMenuCommand;

import com.game.entities.Entity;
import com.game.multiplayer.Client;
import com.game.multiplayer.IOManager;
import com.game.multiplayer.MultiplayerAction;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JoinCommand implements IMenuCommand{
    Entity player;

    public JoinCommand(Entity player) {
        this.player = player;
    }

    @Override
    public void execute() {
        try {
            List<InetAddress> Servers = Client.LookForServers();
            if(Servers.isEmpty()){
                System.out.println("No available servers! Returning to main menu!");
                return;
            }
            List<String> addressesLiteral = new ArrayList<>();
            for(InetAddress inetAddress : Servers){
                System.out.println("Server " + inetAddress.toString().substring(1) + " is available!");
                addressesLiteral.add(inetAddress.toString().substring(1));
            }
            System.out.println("Provide IP of desired server!");
            Scanner ipScanner = new Scanner(System.in);
            int tryNumber = 1;
            String providedIP = ipScanner.nextLine();
            while(true){
                if(tryNumber > 2){
                    return;
                }
                else{
                    if(addressesLiteral.contains(providedIP)){
                        break;
                    }
                    System.out.println("Provided IP has not available server!");
                    providedIP = ipScanner.nextLine();
                    tryNumber++;
                }
            }

            Socket clientSocket = new Socket(providedIP, 5000);
            IOManager ioManager = new IOManager(clientSocket);
            ioManager.sendMessage(MultiplayerAction.WANTTOCONNET);
            Client newClient = new Client(clientSocket, ioManager, this.player);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
