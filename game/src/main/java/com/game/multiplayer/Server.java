package com.game.multiplayer;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//listen server
public class Server{
    private static ServerSocket serverSocket;
    private static ServerState serverState = ServerState.IDLE;
    private boolean bServerMove = false;
    final private static int playerNum = 1;
    private static int roundNumber = 0;
    private static NetRole netRole = NetRole.SERVER;

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

        serverState = ServerState.GAME_IN_PROGRESS;

        String testStr = ioManager.readMessage();
        System.out.println("client message"+testStr);
        ioManager.sendMessage("connection established");

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
        //TODO:restore players health
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
        }

        if(bAnyPlayerDead) {
            //TODO:check if player2 is dead
            if(true) {
                player1Score++;
            }
            else{
                player2Score++;
            }
        }
    }

    private void proceedServerMove(){

    }

    private void proceedClientMove(){

    }

    private void endGame() {
        //TODO:update leaderboard
    }
}