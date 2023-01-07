import com.game.Command.CommandExecutor;
import com.game.Command.SelectionCharacterCommand;
import com.game.controllers.PlayerEntityController;
import com.game.entities.Entity;
import com.game.entities.User;
import com.game.entitiesFactories.PlayerEntityFactory;
import com.game.multiplayer.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MultiplayerTests {

    PlayerEntityFactory entitiesFactory;
    String entityName = "mockCharackter";
    String properType = "Archer";

    @BeforeEach
    void InitializateTest() {
        User user = new User();
        user.setLogin("mock");
        PlayerEntityController playerEntityController = new PlayerEntityController(user);
        entitiesFactory = new PlayerEntityFactory(playerEntityController);
    }

    @Test
    void ShouldReturnSameServerInstance() {
        Server server1 = Server.getInstance();
        Server server2 = Server.getInstance();

        assertEquals(server1,server2);
    }

    @Test
    void ShouldClientFoundNoServers(){
        try {
            List<InetAddress> servers = Client.LookForServers();
            assertTrue(servers.isEmpty());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void ShouldClientSeeLocalServer(){
        final CyclicBarrier gate = new CyclicBarrier(3);

        Thread t1 = new Thread(){
            public void run(){
                try {
                    gate.await();
                } catch (Exception e) {

                }
                Entity serverEntity = entitiesFactory.createEntity(properType, entityName, null);
                Server server = Server.getInstance();
                server.Setup(serverEntity);
            }};
        Thread t2 = new Thread(){
            public void run(){
                try {
                    gate.await();
                } catch (Exception e) {

                }
                Entity clientEntity = entitiesFactory.createEntity(properType, entityName, null);
                List<InetAddress> Servers = new ArrayList<>();
                try {
                    Servers = Client.LookForServers();
                    InetAddress localhost = InetAddress.getByName("127.0.0.1");
                    assertTrue(Servers.contains(localhost));
                    gate.reset();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }};

        t1.start();
        t2.start();

        try {
            gate.await();
            gate.await();
            t1.stop();
        } catch (Exception e) {

        }
    }

    @Test
    void ShouldServerBeInProperState(){
        final CyclicBarrier gate = new CyclicBarrier(3);
        Server server = Server.getInstance();
        Thread t1 = new Thread(){
            public void run(){
                try {
                    gate.await();
                } catch (Exception e) {

                }
                Entity serverEntity = entitiesFactory.createEntity(properType, entityName, null);
                server.Setup(serverEntity);
            }};
        Thread t2 = new Thread(){
            public void run(){
                try {
                    gate.await();
                } catch (Exception e) {

                }
                Entity clientEntity = entitiesFactory.createEntity(properType, entityName, null);
                try {
                    Socket clientSocket = new Socket(InetAddress.getByName("127.0.0.1"), 5000);
                    IOManager ioManager = new IOManager(clientSocket);
                    assertEquals(ServerState.WAIT_FOR_CONNECTION, server.serverState);
                    ioManager.sendMessage(MultiplayerAction.WANTTOCONNET);
                    TimeUnit.MILLISECONDS.sleep(100);
                    assertEquals(ServerState.STARTING_GAME, server.serverState);
                    ioManager.sendObject(clientEntity);
                    TimeUnit.MILLISECONDS.sleep(100);
                    assertEquals(ServerState.GAME_IN_PROGRESS, server.serverState);
                    gate.reset();
                } catch (Exception e) {
                }
            }};

        t1.start();
        t2.start();

        try {
            gate.await();
            gate.await();
            t1.stop();
            t2.stop();
        } catch (Exception e) {

        }
    }

    @Test
    void ShouldServerReceiveSentObject(){
        final CyclicBarrier gate = new CyclicBarrier(3);
        Server server = Server.getInstance();
        Thread t1 = new Thread(){
            public void run(){
                try {
                    gate.await();
                } catch (Exception e) {

                }
                Entity serverEntity = entitiesFactory.createEntity(properType, entityName, null);
                server.Setup(serverEntity);
            }};
        Thread t2 = new Thread(){
            public void run(){
                try {
                    gate.await();
                } catch (Exception e) {

                }
                Entity clientEntity = entitiesFactory.createEntity(properType, entityName, null);
                try {
                    Socket clientSocket = new Socket(InetAddress.getByName("127.0.0.1"), 5000);
                    IOManager ioManager = new IOManager(clientSocket);
                    ioManager.sendMessage(MultiplayerAction.WANTTOCONNET);
                    ioManager.sendObject(clientEntity);
                    TimeUnit.MILLISECONDS.sleep(100);
                    assertEquals(clientEntity.getNameInfo(), server.getPlayerTwo().getNameInfo());
                    gate.reset();
                } catch (Exception e) {
                }
            }};

        t1.start();
        t2.start();

        try {
            gate.await();
            gate.await();
            t1.stop();
            t2.stop();
        } catch (Exception e) {

        }
    }

    @Test
    void ShouldClientReceiveStartingRole(){
        final CyclicBarrier gate = new CyclicBarrier(3);
        Server server = Server.getInstance();
        Thread t1 = new Thread(){
            public void run(){
                try {
                    gate.await();
                } catch (Exception e) {

                }
                Entity serverEntity = entitiesFactory.createEntity(properType, entityName, null);
                server.Setup(serverEntity);
            }};
        Thread t2 = new Thread(){
            public void run(){
                try {
                    gate.await();
                } catch (Exception e) {

                }
                Entity clientEntity = entitiesFactory.createEntity(properType, entityName, null);
                try {
                    Socket clientSocket = new Socket(InetAddress.getByName("127.0.0.1"), 5000);
                    IOManager ioManager = new IOManager(clientSocket);
                    ioManager.sendMessage(MultiplayerAction.WANTTOCONNET);
                    ioManager.sendObject(clientEntity);

                    gate.reset();
                } catch (Exception e) {
                }
            }};

        t1.start();
        t2.start();

        try {
            gate.await();
            gate.await();
            t1.stop();
            t2.stop();
        } catch (Exception e) {

        }
    }

}
