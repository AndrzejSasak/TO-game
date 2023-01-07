import com.game.Command.CommandExecutor;
import com.game.Command.multiplayerMenuCommand.HostCommand;
import com.game.Command.multiplayerMenuCommand.JoinCommand;
import com.game.controllers.PlayerEntityController;
import com.game.entities.Entity;
import com.game.entities.User;
import com.game.entitiesFactories.PlayerEntityFactory;
import com.game.multiplayer.*;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MultiplayerTests {
    CommandExecutor commandExecutor = new CommandExecutor();
    PlayerEntityFactory entitiesFactory;
    String entityName = "mockCharackter";
    String properType = "Archer";

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;

    @BeforeAll
    static void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterAll
    static void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @BeforeEach
    void InitializateTest() {
        User user = new User();
        user.setLogin("mock");
        PlayerEntityController playerEntityController = new PlayerEntityController(user);
        entitiesFactory = new PlayerEntityFactory(playerEntityController);
        try {
            outContent.flush();
            outContent.reset();
        } catch (IOException e) {
        }
    }

    @Test
    void ShouldReturnSameServerInstance() {
        Server server1 = Server.getInstance();
        Server server2 = Server.getInstance();

        assertEquals(server1,server2);
    }

    @Test
    @Order(1)
    void ShouldClientFoundNoServers(){
        try {
            List<InetAddress> servers = Client.LookForServers();
            Assertions.assertTrue(servers.isEmpty());
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
            t2.stop();
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
                    String receivedMessage = ioManager.readMessage();
                    final List<String> VALID_VALUES = Arrays.asList(MultiplayerAction.CLIENT, MultiplayerAction.SERVER);
                    assertTrue(VALID_VALUES.contains(receivedMessage));
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
    void ShouldStartServerWithHostCommand() {
        final CyclicBarrier gate = new CyclicBarrier(3);
        Thread t1 = new Thread(){
            public void run(){
                try {
                    gate.await();
                } catch (Exception e) {

                }
                Entity serverEntity = entitiesFactory.createEntity(properType, entityName, null);
                HostCommand hostCommand = new HostCommand(serverEntity);
                commandExecutor.executeCommand(hostCommand);
            }};
        Thread t2 = new Thread(){
            public void run(){
                try {
                    gate.await();
                } catch (Exception e) {
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(3000);
                    String outputMessage = outContent.toString();
                    String[] messageSplited = outputMessage.split("\r", 2);
                    assertEquals("Waiting for opponent", messageSplited[0]);
                    gate.reset();
                } catch (InterruptedException e) {
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
    void ShouldStartClientWithJoinCommand() {
        final CyclicBarrier gate = new CyclicBarrier(3);
        Thread t1 = new Thread(){
            public void run(){
                try {
                    gate.await();
                } catch (Exception e) {

                }
                Entity serverEntity = entitiesFactory.createEntity(properType, entityName, null);
                JoinCommand hostCommand = new JoinCommand(serverEntity);
                commandExecutor.executeCommand(hostCommand);
            }};
        Thread t2 = new Thread(){
            public void run(){
                try {
                    gate.await();
                } catch (Exception e) {
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(3000);
                    String outputMessage = outContent.toString();
                    String[] messageSplited = outputMessage.split("\\.", 2);
                    assertEquals("Scanning for open servers", messageSplited[0]);
                    gate.reset();
                } catch (InterruptedException e) {
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
