package com.game.multiplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client{
    public static void main(String[] args) throws IOException {

        Socket clientSocket = new Socket("localhost", 5000);
        IOManager ioManager = new IOManager(clientSocket);

        ioManager.sendMessage("connected");
        String testStr = ioManager.readMessage();

        System.out.println("Server message "+ testStr);
    }
}