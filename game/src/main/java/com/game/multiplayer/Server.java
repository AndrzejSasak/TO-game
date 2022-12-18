package com.game.multiplayer;

import com.game.controllers.PlayerEntityController;
import com.game.controllers.RemotePlayerEntityController;
import com.game.entities.Archer;
import com.game.entities.Entity;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//listen server
public class Server{
    private static ServerSocket serverSocket;
    private static ServerState serverState = ServerState.IDLE;
    private boolean bServerMove = false;
    final private static int playerNum = 1;
    private static int roundNumber = 0;
    private static NetRole netRole = NetRole.SERVER;
    private Entity playerOne;//host
    private Entity playerTwo;//client
    private IOManager ioManager;
    private static int player1Score = 0;
    private static int player2Score = 0;

    private static NetRole roleStartedLastRound;

    private static volatile Server INSTANCE;

    public static Server getInstance(){
        if(null == INSTANCE){
            try{
                INSTANCE = new Server();
            }
            catch (Exception e){
                System.out.println("Error while creating server");
            }
        }
        return INSTANCE;
    }

    private Server() throws IOException {
         serverSocket = new ServerSocket(5000);
    }

    public void Setup(Entity player) throws IOException, InterruptedException {
        this.playerOne = player;
        gameLoop();
    }

    private void gameLoop() throws IOException, InterruptedException {
        serverState = ServerState.WAIT_FOR_CONNECTION;

        System.out.println("Waiting for opponent");
        DotPrintService dotPrintService = new DotPrintService();
        dotPrintService.startTimer();
        Socket clientSocket = serverSocket.accept();

        serverState = ServerState.STARTING_GAME;

        dotPrintService.stopTimer();
        ioManager = new IOManager(clientSocket);
        beginGame();
        serverState = ServerState.GAME_IN_PROGRESS;
        progressRound();
        onEndRound();

        while(player1Score != 3 && player2Score != 3)
        {
            onBeginRound();
            progressRound();
            onEndRound();
        }

        System.out.println("Game ended with result: "+player1Score+" "+player2Score);
        System.out.println(player1Score>player2Score ? "Player One won game" : "Player two won the game");

        serverState = ServerState.END_GAME;
    }

    private void beginGame() {
        try {
            playerTwo = (Entity)ioManager.receiveObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Random random = new Random();
        roleStartedLastRound = random.nextBoolean() ? NetRole.SERVER : NetRole.CLIENT;
        String toSend = roleStartedLastRound == NetRole.SERVER ? "Server" : "Client";
        ioManager.sendMessage(toSend);
        System.out.println("server begin game " + toSend);
        roundNumber++;
    }

    private void onBeginRound() {
        roleStartedLastRound = roleStartedLastRound == NetRole.CLIENT ? NetRole.SERVER : NetRole.CLIENT;
        roundNumber++;
    }

    private void progressRound() {
        boolean bAnyPlayerDead = false;
        RemotePlayerEntityController remotePlayerEntityController = (RemotePlayerEntityController) playerOne.getController();
        while (!bAnyPlayerDead){
            if(roleStartedLastRound == NetRole.SERVER) {
                proceedServerMove(remotePlayerEntityController);
                proceedClientMove();
            }
            else {
                proceedClientMove();
                proceedServerMove(remotePlayerEntityController);
            }
            bAnyPlayerDead = playerOne.isDead() || playerTwo.isDead();
        }

        if(bAnyPlayerDead) {
            if(playerTwo.isDead()) {
                player1Score++;
            }
            else{
                player2Score++;
            }
            return;
        }
    }

    private void onEndRound(){
        playerOne.revive();
        playerTwo.revive();
    }

    private void proceedServerMove(RemotePlayerEntityController remotePlayerEntityController){
        System.out.println("proceedServerMove start");
        remotePlayerEntityController.performMultiplayerAction(playerOne);
        if(playerOne.bWantsToAttack){
            playerOne.multiplayerAttack(playerTwo);
            playerTwo.setCritical(false);
        }
        System.out.println("proceedServerMove end");
        ioManager.sendMessage("test");
    }

    private void proceedClientMove(){
        String line;
        try{
            System.out.println("proceedClientMove start ");
            line = ioManager.readMessage();
            if(line.equalsIgnoreCase( "wait")){
                playerTwo.bWantsToAttack = false;
                playerTwo.bWantsToWait = true;
                playerTwo.setCritical(true);
            }
            else if(line.equalsIgnoreCase("attack")){
                playerTwo.bWantsToWait = false;
                playerTwo.bWantsToAttack = true;
            }
            if(playerTwo.bWantsToAttack){
                playerTwo.multiplayerAttack(playerOne);
                playerTwo.setCritical(false);
            }
            System.out.println("proceedClientMove end");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void endGame() {
        //TODO:update leaderboard
    }
}