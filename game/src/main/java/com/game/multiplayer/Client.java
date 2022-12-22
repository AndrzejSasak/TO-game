package com.game.multiplayer;

import com.game.controllers.PlayerEntityController;
import com.game.controllers.RemotePlayerEntityController;
import com.game.entities.Entity;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Client{
    Socket clientSocket;
    IOManager ioManager;
    Entity player;
    GameStatus gameStatus= GameStatus.NONE;
    int myScore = 0;
    int otherPlayerScore = 0;
    int roundNumber = 0;
    public Client(Socket clientSocket, IOManager ioManager, Entity player) throws IOException {
        this.clientSocket = clientSocket;
        this.ioManager = ioManager;
        this.player = player;



        clientJoinGame();
    }

    public void clientJoinGame() throws IOException {
        ioManager.sendObject(player);
        String fromServer;
        fromServer = ioManager.readMessage();
        boolean serverStartRound = fromServer.equals(MultiplayerAction.SERVER);
        while(true){
            ProcessRound(serverStartRound);
            fromServer = ioManager.readMessage();
            if(fromServer.equals(MultiplayerAction.END_OF_GAME)){
                break;
            }
            serverStartRound = !serverStartRound;
        }
        if(myScore > otherPlayerScore){
            System.out.println(MultiplayerAction.WON_GAME);
        }
        else{
            System.out.println(MultiplayerAction.LOST_GAME);
        }
    }

    private void ProcessRound(boolean bServerStart) throws IOException{
        if(bServerStart) {
            ProcessRoundServerStart();
        }
        else{
            ProcessRoundClientStart();
        }
        String winner = ioManager.readMessage();
        if(winner.equals(MultiplayerAction.CLIENT)){
            myScore++;
            System.out.println(MultiplayerAction.WON_ROUND);
        }
        else {
            otherPlayerScore++;
            System.out.println(MultiplayerAction.LOST_ROUND);
        }
        System.out.println("After round " + roundNumber + "Score: "+ "me"+ " " + myScore+" - "+ otherPlayerScore + " " + "opponent");
    }

    private void ProcessRoundServerStart() throws IOException{
        RemotePlayerEntityController remotePlayerEntityController = (RemotePlayerEntityController) player.getController();
        String fromServer;
        System.out.println(ioManager.readMessage());
        fromServer = ioManager.readMessage();
        while(fromServer != null){
            if(fromServer.equals(MultiplayerAction.END_OF_ROUND))
            {
                return;
            }
            PerformAction(remotePlayerEntityController, player);
            System.out.println(ioManager.readMessage());
            System.out.println(ioManager.readMessage());
            fromServer = ioManager.readMessage();
        }
    }

    private void ProcessRoundClientStart() throws IOException{
        String fromServer;
        fromServer = ioManager.readMessage();
        RemotePlayerEntityController remotePlayerEntityController = (RemotePlayerEntityController) player.getController();
        PerformAction(remotePlayerEntityController, player);
        System.out.println(ioManager.readMessage());
        fromServer = ioManager.readMessage();
        while(fromServer != null){
            System.out.println(fromServer);
            fromServer = ioManager.readMessage();
            if(fromServer.equals(MultiplayerAction.END_OF_ROUND)) {
                return;
            }
            PerformAction(remotePlayerEntityController, player);
            System.out.println(ioManager.readMessage());
            fromServer = ioManager.readMessage();
        }
    }

    private void PerformAction(RemotePlayerEntityController remotePlayerEntityController, Entity player){
        remotePlayerEntityController.performMultiplayerAction(player);
        ioManager.sendMessage(player.bWantsToAttack ? MultiplayerAction.ATTACK : MultiplayerAction.WAIT);
        player.setCritical(!player.bWantsToAttack);
    }

    public static List<InetAddress> LookForServers() throws UnknownHostException {
        int port = 5000;
        List<InetAddress> ips = getAvailableIps();
        List<InetAddress> possibleServers = new ArrayList<>();
        for(InetAddress valid : ips){
            Socket s = null;
            try {
                s = new Socket(valid.toString().substring(1), port);
                IOManager ioManager = new IOManager(s);
                ioManager.sendMessage(MultiplayerAction.LOOKING);
                ioManager.readMessage();
                possibleServers.add(valid);
            } catch (IOException e) {

            } finally {
                if( s != null){
                    try {
                        s.close();
                    } catch (IOException e) {
                        throw new RuntimeException("You should handle this error." , e);
                    }
                }
            }
        }

        return possibleServers;
    }

    public static List<InetAddress> getAvailableIps() throws UnknownHostException {
        InetAddress localhost = InetAddress.getLocalHost();
        byte[] ip = localhost.getAddress();
        List<InetAddress> ips = new ArrayList<>();
        for (int i = 1; i <= 254; i++) {
            ip[3] = (byte)i;
            InetAddress address = InetAddress.getByAddress(ip);
            ips.add(address);
        }
        ips.add(InetAddress.getByName("127.0.0.1"));
        DotPrintService dotPrintService = new DotPrintService();
        System.out.print("Scanning for open servers");
        dotPrintService.startTimer();
        List<InetAddress> validAddresses = ips.parallelStream().filter(Client::checkIp).collect(Collectors.toList());;
        dotPrintService.stopTimer();
        return validAddresses;
    }

    public static boolean checkIp(InetAddress address) {
        try {
            if (address.isReachable(100)) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


}