import com.game.Command.CommandExecutor;
import com.game.Command.SelectionCharacterCommand;
import com.game.controllers.PlayerEntityController;
import com.game.entities.Entity;
import com.game.entities.User;
import com.game.entitiesFactories.PlayerEntityFactory;
import com.game.sharedUserInterface.SelectEntityType;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static  org.junit.jupiter.api.Assertions.*;

public class SelectionCharacterTest {

    CommandExecutor commandExecutor = new CommandExecutor();
    User user;
    PlayerEntityController playerEntityController;
    Entity entity;
    PlayerEntityFactory entitiesFactory;
    String entityName = "mockCharackter";
    String properType = "Archer";
    String badType = "badType";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream sysInBackup = System.in;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setIn(sysInBackup);
        System.setOut(originalOut);
    }

    @BeforeEach
    void InitializateTest() {
        User user = new User();
        user.setLogin("mock");
        PlayerEntityController playerEntityController = new PlayerEntityController(user);
        entitiesFactory = new PlayerEntityFactory(playerEntityController);
    }

    @Test
    void ShouldSelectArcher() {
        Entity newEntity = entitiesFactory.createEntity(properType, entityName, null);

        SelectionCharacterCommand selectionCharacterCommand = new SelectionCharacterCommand(entity, newEntity);
        commandExecutor.executeCommand(selectionCharacterCommand);

        entity = selectionCharacterCommand.getEntity();

        assertEquals(entity.getProfessionName(), properType);
    }

    @Test
    void ShouldChangeCharacterName() {
        entity = entitiesFactory.createEntity(properType, entityName, null);
        String newName = "Arrow";
        Entity newEntity = entitiesFactory.createEntity(properType, newName, null);

        SelectionCharacterCommand selectionCharacterCommand = new SelectionCharacterCommand(entity, newEntity);
        commandExecutor.executeCommand(selectionCharacterCommand);

        entity = selectionCharacterCommand.getEntity();

        assertEquals(entity.getName(), "Arrow");
    }

    @Test
    void ShouldntCreateEntityWithWrongSelecetedType() {
        entity = entitiesFactory.createEntity(badType, entityName, null);

        assertNull(entity);
    }

    @Test
    void ShouldDiplayMessageWhenUserDontProvideNameOnFirstCharacterSelection() {
        String message = "Provide name for player!";
        ByteArrayInputStream in = new ByteArrayInputStream((properType + "\n" + properType + " " + entityName).getBytes());
        System.setIn(in);
        entity = new SelectEntityType().createNewEntity(entity, playerEntityController);

        String[] output = outContent.toString().split("\n");
        assertEquals(message, output[output.length - 1].trim());
    }

    @Test
    void ShouldDiplayMessageWhenUserProvideBadClassName() {
        String message = "Please type valid character class";
        ByteArrayInputStream in = new ByteArrayInputStream((badType + " " + entityName + "\n" + properType + " " + entityName).getBytes());
        System.setIn(in);
        entity = new SelectEntityType().createNewEntity(entity, playerEntityController);

        String[] output = outContent.toString().split("\n");
        assertEquals(message, output[output.length - 1].trim());
    }
}
