package com.game.multiplayer;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class IOManager {
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;
    private PrintWriter pr;
    private InputStreamReader in;
    private BufferedReader bf;

    public IOManager(Socket socket) throws IOException {
         objectOut = new ObjectOutputStream(socket.getOutputStream());
         objectIn = new ObjectInputStream(socket.getInputStream());
         pr = new PrintWriter(socket.getOutputStream());
         in = new InputStreamReader(socket.getInputStream());
         bf = new BufferedReader(in);
    }

    public void sendMessage(String message) throws SocketException {
        pr.println(message);
        pr.flush();
    }

    public void sendObject(Object object) throws IOException, SocketException {
        objectOut.writeObject(object);
    }

    public Object receiveObject() throws IOException, ClassNotFoundException, SocketException {
        return objectIn.readObject();
    }

    public String readMessage() throws IOException, SocketException {
        return bf.readLine();
    }
}
