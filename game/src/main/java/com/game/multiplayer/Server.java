package com.game.multiplayer;

import com.game.controllers.PlayerEntityController;
import com.game.entities.Archer;
import com.game.entities.Entity;

import java.util.Random;
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

    Entity playerOne = new Archer("Adrian", new PlayerEntityController());
    Entity playerTwo = new Archer("Konrad", new PlayerEntityController());

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

    public static void main(String[] args) throws IOException, InterruptedException {
        new Server().gameLoop();
    }

    private void gameLoop() throws IOException, InterruptedException {
        serverState = ServerState.WAIT_FOR_CONNECTION;

        System.out.println("Waiting for opponent");
        DotPrintService dotPrintService = new DotPrintService();
        dotPrintService.startTimer();
        Socket clientSocket = serverSocket.accept();

        serverState = ServerState.STARTING_GAME;

        dotPrintService.stopTimer();
        System.out.println("client connected");
        TimeUnit.SECONDS.sleep(3);

        IOManager ioManager = new IOManager(clientSocket);
        beginGame();
        serverState = ServerState.GAME_IN_PROGRESS;

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
        Random random = new Random();
        roleStartedLastRound = random.nextBoolean() ? NetRole.SERVER : NetRole.CLIENT;
        roundNumber++;
    }

    private void onBeginRound() {
        roleStartedLastRound = roleStartedLastRound == NetRole.CLIENT ? NetRole.SERVER : NetRole.CLIENT;
        roundNumber++;
    }

    private void progressRound() {
        boolean bAnyPlayerDead = false;

        while (!bAnyPlayerDead){
            if(roleStartedLastRound == NetRole.SERVER) {
                proceedServerMove();
                proceedClientMove();
            }
            else {
                proceedClientMove();
                proceedServerMove();
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

    private void proceedServerMove(){
        playerTwo.getHit(playerOne.getAttackPoints(),playerOne, null, null);
    }

    private void proceedClientMove(){
        playerOne.getHit(playerTwo.getAttackPoints(),playerTwo, null, null);
    }

    private void endGame() {
        //TODO:update leaderboard
    }
}