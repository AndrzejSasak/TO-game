package com.game.multiplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client{
    public static void main(String[] args) throws IOException {

        Socket clientSocket = new Socket("localhost", 5000);

        PrintWriter pr = new PrintWriter(clientSocket.getOutputStream());
        pr.println("Client connected");
        pr.flush();

        InputStreamReader in = new InputStreamReader(clientSocket.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        String testStr = bf.readLine();
        System.out.println("Server message "+testStr);
    }
}