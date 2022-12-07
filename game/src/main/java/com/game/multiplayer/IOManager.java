package com.game.multiplayer;

import java.io.*;
import java.net.Socket;

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

    public void sendMessage(String message) {
        pr.println(message);
        pr.flush();
    }

    public void sendObject(Object object) throws IOException {
        objectOut.writeObject(object);
    }

    public Object receiveObject() throws IOException, ClassNotFoundException {
        return objectIn.readObject();
    }

    public String readMessage() throws IOException {
        return bf.readLine();
    }
}
