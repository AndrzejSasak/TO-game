package com.game.multiplayer;

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

    public Client(Socket clientSocket, IOManager ioManager) throws IOException {
        this.clientSocket = clientSocket;
        this.ioManager = ioManager;

        clientJoinGame();
    }

    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket("localhost", 5000);
        IOManager ioManager = new IOManager(clientSocket);
        Client newClient = new Client(clientSocket, ioManager);

    }

    public void LookForServers() throws UnknownHostException {
        int port = 5000;
        List<InetAddress> ips = getAvailableIps();

        for(InetAddress valid : ips){
            Socket s = null;
            try {
                s = new Socket(valid.toString().substring(1), port);
                System.out.println(valid.toString().substring(1) + " is not available");
            } catch (IOException e) {
                System.out.println(valid.toString().substring(1) + " is available");
            } finally {
                if( s != null){
                    try {
                        s.close();
                    } catch (IOException e) {
                        throw new RuntimeException("You should handle this error." , e);
                    }
                }
            }
            //System.out.println(valid.toString().substring(1));
        }
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

    public void clientJoinGame() throws IOException {
        ioManager.sendMessage("connected");
        String testStr = ioManager.readMessage();

        System.out.println("Server message "+ testStr);
    }
}