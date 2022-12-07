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

    private static ServerState serverState = ServerState.IDLE;
    private boolean bServerMove = false;
    final private static int playerNum = 1;
    private static int roundNumber = 0;
    private static NetRole netRole = NetRole.SERVER;

    private static int player1Score = 0;
    private static int player2Score = 0;

    private static NetRole roleStartedLastRound;

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(5000);

        serverState = ServerState.WAIT_FOR_CONNECTION;
        System.out.println("Waiting for opponent");
        DotPrintService dotPrintService = new DotPrintService();
        dotPrintService.startTimer();

        Socket clientSocket = serverSocket.accept();

        serverState = ServerState.STARTING_GAME;
        dotPrintService.stopTimer();
        System.out.println("\nclient connected");
        TimeUnit.SECONDS.sleep(3);

        PrintWriter pr = new PrintWriter(clientSocket.getOutputStream());
        InputStreamReader in = new InputStreamReader(clientSocket.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        serverState = ServerState.GAME_IN_PROGRESS;
        String testStr = bf.readLine();
        System.out.println("client message"+testStr);
        pr.println("connection established");
        pr.flush();

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