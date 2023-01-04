import com.game.Command.CommandExecutor;
import com.game.Command.SelectionCharacterCommand;
import com.game.controllers.PlayerEntityController;
import com.game.entities.Entity;
import com.game.entities.User;
import com.game.entitiesFactories.PlayerEntityFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static  org.junit.jupiter.api.Assertions.*;

public class SelectionCharacterTest {

    CommandExecutor commandExecutor = new CommandExecutor();
    User user;
    PlayerEntityController playerEntityController;
    Entity entity;
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
        entity = entitiesFactory.createEntity("badType", entityName, null);

        assertNull(entity);
    }
}
