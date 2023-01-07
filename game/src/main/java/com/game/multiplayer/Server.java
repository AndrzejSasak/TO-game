package com.game.multiplayer;

import com.game.controllers.PlayerEntityController;
import com.game.controllers.RemotePlayerEntityController;
import com.game.entities.Archer;
import com.game.entities.Entity;
import com.game.entities.User;
import com.game.entities.User;
import com.game.leaderboard.ILeadeboardParser;
import com.game.leaderboard.Leaderboard;
import com.game.leaderboard.LeaderboardParserProxy;
import com.game.sharedUserInterface.LocalMessages;
import jakarta.xml.bind.JAXBException;

import java.io.ByteArrayOutputStream;
import java.net.SocketException;
import java.security.spec.ECField;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Math.abs;
import static java.lang.Math.random;

//listen server
public class Server{
    private static ServerSocket serverSocket;
    public static ServerState serverState = ServerState.IDLE;
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
    private static boolean winnerPlayerOne = false;

    private static volatile Server INSTANCE;

    public Entity getPlayerOne() {
        return playerOne;
    }

    public Entity getPlayerTwo() {
        return playerTwo;
    }

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

    public void Setup(Entity player){
        this.playerOne = player;
        try{
            gameLoop();
        }
        catch(Exception e){
            System.out.println("Connection lost! Backing to main menu!");
        }
    }

    private void gameLoop() throws IOException, InterruptedException, SocketException {
        serverState = ServerState.WAIT_FOR_CONNECTION;

        System.out.println("Waiting for opponent");
        DotPrintService dotPrintService = new DotPrintService();
        dotPrintService.startTimer();
        Socket clientSocket = serverSocket.accept();
        try{
            ioManager = new IOManager(clientSocket);
        }
        catch (Exception E){
            System.out.println("Unable to create io manager!");
        }

        String fromClient = "";
        if(ioManager != null) {
            fromClient = ioManager.readMessage();
        }
        while(fromClient != null){
            if(fromClient.equalsIgnoreCase(MultiplayerAction.WANTTOCONNET)) {
                break;
            }
            if(ioManager != null) {
                ioManager.sendMessage("Close");
            }
            clientSocket = serverSocket.accept();
            ioManager = new IOManager(clientSocket);
            fromClient = ioManager.readMessage();
        }
        serverState = ServerState.STARTING_GAME;

        dotPrintService.stopTimer();
        beginGame();
        serverState = ServerState.GAME_IN_PROGRESS;
        progressRound();
        onEndRound();

        while(player1Score != 3 && player2Score != 3)
        {
            LocalMessages.displayVerticalLine();
            System.out.println("After round " + roundNumber + "Score: "+ playerOne.getName() + " " + player1Score+" - "+ player2Score + " " + playerTwo.getName());
            LocalMessages.displayVerticalLine();
            onBeginRound();
            progressRound();
            onEndRound();
        }
        ioManager.sendMessage(MultiplayerAction.END_OF_GAME);
        System.out.println("Game ended with result: "+player1Score+" "+player2Score);
        System.out.println(player1Score>player2Score ? MultiplayerAction.WON_GAME : MultiplayerAction.LOST_GAME);
        winnerPlayerOne = player1Score>player2Score;
        endGame();
        serverState = ServerState.END_GAME;
    }

    private void beginGame() throws SocketException{
        try {
            playerTwo = (Entity)ioManager.receiveObject();
        } catch (IOException e) {
            System.out.println("Unable to perform io action!");
        } catch (ClassNotFoundException e) {
            System.out.println("Provided class for cast is not supported type");
        }
        player1Score = 0;
        player2Score = 0;
        Random random = new Random();
        roleStartedLastRound = random.nextBoolean() ? NetRole.SERVER : NetRole.CLIENT;
        String toSend = roleStartedLastRound == NetRole.SERVER ? MultiplayerAction.SERVER : MultiplayerAction.CLIENT;
        ioManager.sendMessage(toSend);
        roundNumber++;
    }

    private void onBeginRound() {
        roleStartedLastRound = roleStartedLastRound == NetRole.CLIENT ? NetRole.SERVER : NetRole.CLIENT;
        roundNumber++;
    }

    private void progressRound() throws SocketException{
        boolean bAnyPlayerDead = false;
        RemotePlayerEntityController remotePlayerEntityController = (RemotePlayerEntityController) playerOne.getController();
        while (!bAnyPlayerDead){
            if(roleStartedLastRound == NetRole.SERVER) {
                proceedServerMove(remotePlayerEntityController);
                if(playerTwo.isDead()) {
                    break;
                }
                proceedClientMove();
            }
            else {
                proceedClientMove();
                if(playerOne.isDead()) {
                    break;
                }
                proceedServerMove(remotePlayerEntityController);
            }
            bAnyPlayerDead = playerOne.isDead() || playerTwo.isDead();
        }
    }

    private void onEndRound() throws SocketException{
        boolean bClientWinner = false;
        if(playerTwo.isDead()) {
            player1Score++;
            System.out.println(MultiplayerAction.WON_ROUND);
        }
        else{
            player2Score++;
            bClientWinner = true;
        }

        playerOne.revive();
        playerTwo.revive();
        ioManager.sendMessage(MultiplayerAction.END_OF_ROUND);
        ioManager.sendMessage(bClientWinner ? MultiplayerAction.CLIENT : MultiplayerAction.SERVER);
        if(player1Score != 3 && player2Score != 3){
            ioManager.sendMessage(MultiplayerAction.NEXT_ROUND);
        }
    }

    private void proceedServerMove(RemotePlayerEntityController remotePlayerEntityController) throws SocketException{
        remotePlayerEntityController.performMultiplayerAction(playerOne);
        if(playerOne.bWantsToAttack){
            int dmg = playerTwo.getHp();
            playerOne.multiplayerAttack(playerTwo);
            playerOne.setCritical(false);
            dmg -= playerTwo.getHp();
            ioManager.sendMessage(playerOne.getName() + " attacked you and dealt " + dmg + "dmg! " +
                    playerTwo.getName() +" (" + playerTwo.getHp() + "/" + playerTwo.getMaxHp() + ") " + playerOne.getName() +" (" + playerOne.getHp() + "/" + playerOne.getMaxHp() + ")");
        }
        else{
            ioManager.sendMessage(playerOne.getName() + " is generating boosted attack! " +
                    playerTwo.getName() +" (" + playerTwo.getHp() + "/" + playerTwo.getMaxHp() + ") " + playerOne.getName() +" (" + playerOne.getHp() + "/" + playerOne.getMaxHp() + ")");
        }
    }

    private void proceedClientMove() throws SocketException{
        System.out.println("Waiting for "+ playerTwo.getName() + " move");
        ioManager.sendMessage(MultiplayerAction.CLIENT_TURN);
        String line;
        try{
            line = ioManager.readMessage();
            if(line.equalsIgnoreCase(MultiplayerAction.WAIT)){
                playerTwo.bWantsToAttack = false;
                playerTwo.bWantsToWait = true;
                playerTwo.setCritical(true);
                System.out.println(playerTwo.getName() + " is generating boosted attack!");
                ioManager.sendMessage("You are generating boosted attack! " +
                        playerTwo.getName() +" (" + playerTwo.getHp() + "/" + playerTwo.getMaxHp() + ") " + playerOne.getName() +" (" + playerOne.getHp() + "/" + playerOne.getMaxHp() + ")");
            }
            else if(line.equalsIgnoreCase(MultiplayerAction.ATTACK)){
                playerTwo.bWantsToWait = false;
                playerTwo.bWantsToAttack = true;
            }
            if(playerTwo.bWantsToAttack){
                int dmg = playerOne.getHp();
                playerTwo.multiplayerAttack(playerOne);
                playerTwo.setCritical(false);
                dmg -= playerOne.getHp();
                ioManager.sendMessage("You have attacked "+ playerOne.getName() +" and dealt " + dmg + "dmg! " +
                        playerTwo.getName() +" (" + playerTwo.getHp() + "/" + playerTwo.getMaxHp() + ") " + playerOne.getName() +" (" + playerOne.getHp() + "/" + playerOne.getMaxHp() + ")");
            }
        }
        catch (IOException e){
            System.out.println("Unable to perform socket action!");
            throw new SocketException();
        }

    }

    private void endGame() throws SocketException{
        ioManager.sendMessage(MultiplayerAction.END_OF_GAME);
        ILeadeboardParser leadeboardParser = new LeaderboardParserProxy();

        try {
            Leaderboard leaderboard = leadeboardParser.readLeaderboard();
            System.out.println(leaderboard);
            UpdateLeaderboard(leaderboard);
            addPlayerToLeaderboard(leaderboard, this.playerOne);
            addPlayerToLeaderboard(leaderboard, this.playerTwo);
            System.out.println(leaderboard);
            leadeboardParser.saveLeaderboard(leaderboard);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    private void addPlayerToLeaderboard(Leaderboard leaderboard, Entity player) {
        Optional<User> userOptional = player.getController().getRealPlayerEntityOwner();
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            leaderboard.getUsers().remove(user);
            leaderboard.addUser(user);
        }
    }

    private void UpdateLeaderboard(Leaderboard leaderboard){
        Optional<User> userOneOptional = playerOne.getController().getRealPlayerEntityOwner();
        Optional<User> userTwoOptional = playerTwo.getController().getRealPlayerEntityOwner();
        if(userOneOptional.isPresent() && userTwoOptional.isPresent()){
            User userOne = userOneOptional.get();
            User userTwo = userTwoOptional.get();
            Set<User> userSet = leaderboard.getUsers();
            long userOneScore = 0;
            long userTwoScore = 0;
            for(User currentUser : userSet){
                if(currentUser.getLogin().equals(userOne.getLogin())){
                    userOneScore = currentUser.getHighScore();
                }
                if(currentUser.getLogin().equals(userTwo.getLogin())){
                    userTwoScore = currentUser.getHighScore();
                }
            }
            long scoreChange = 0;
            long scoreDiff = abs(userOneScore - userTwoScore);
            boolean userOneHasHigherScore = userOneScore > userTwoScore;
            if((userOneHasHigherScore && winnerPlayerOne) || (!userOneHasHigherScore && !winnerPlayerOne))
            {
                if(scoreDiff > 800){
                    scoreChange = ThreadLocalRandom.current().nextInt(1, 4);
                }
                else if(scoreDiff > 400){
                    scoreChange = ThreadLocalRandom.current().nextInt(3, 8);
                }
                else if(scoreDiff > 150){
                    scoreChange = ThreadLocalRandom.current().nextInt(7, 12);
                }
                else if(scoreDiff > 50){
                    scoreChange = ThreadLocalRandom.current().nextInt(11, 16);
                }
                else{
                    scoreChange = ThreadLocalRandom.current().nextInt(15, 26);
                }
            }
            else
            {
                if(scoreDiff > 800){
                    scoreChange = ThreadLocalRandom.current().nextInt(40, 51);
                }
                else if(scoreDiff > 400){
                    scoreChange = ThreadLocalRandom.current().nextInt(25, 36);
                }
                else if(scoreDiff > 150){
                    scoreChange = ThreadLocalRandom.current().nextInt(7, 12);
                }
                else if(scoreDiff > 50){
                    scoreChange = ThreadLocalRandom.current().nextInt(11, 16);
                }
                else{
                    scoreChange = ThreadLocalRandom.current().nextInt(15, 26);
                }
            }
            if(winnerPlayerOne) {
                userOne.setHighScore(userOneScore + scoreChange > 2000 ? 2000 : userOneScore + scoreChange);
                userTwo.setHighScore(userTwoScore - scoreChange < 0 ? 0 : userTwoScore - scoreChange);
            }
            else{
                userOne.setHighScore(userOneScore - scoreChange < 0 ? 0 : userOneScore - scoreChange);
                userTwo.setHighScore(userTwoScore + scoreChange > 2000 ? 2000 : userTwoScore + scoreChange);
            }
        }
    }
}