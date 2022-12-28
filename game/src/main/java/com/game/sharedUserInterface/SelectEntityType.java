package com.game.sharedUserInterface;

import com.game.Command.CommandExecutor;
import com.game.Command.SelectionCharacterCommand;
import com.game.controllers.PlayerEntityController;
import com.game.entities.*;
import com.game.entitiesFactories.EntitiesFactory;
import com.game.entitiesFactories.PlayerEntityFactory;

import java.util.Scanner;

public class SelectEntityType {
    private Entity entity;
    private Entity newEntity;
    private PlayerEntityController playerEntityController;

    public Entity createNewEntity(Entity entity, PlayerEntityController playerEntityController) {
        return this.setEntity(entity)
                .setPlayerController(playerEntityController)
                .selectPlayerEntity()
                .getEntity();
    }

    public Entity updateEntity(Entity entity) {
        return this.setEntity(entity)
                .setPlayerController((PlayerEntityController) entity.getController())
                .selectPlayerEntity()
                .getEntity();
    }

    SelectEntityType setPlayerController(PlayerEntityController playerEntityControllerController) {
        this.playerEntityController = playerEntityControllerController;
        return this;
    }

    SelectEntityType setEntity(Entity entity) {
        this.entity = entity;
        return this;
    }

    SelectEntityType selectPlayerEntity() {
        boolean selection = false;
        Scanner inputReader = new Scanner(System.in);
        PlayerEntityFactory entitiesFactory = new PlayerEntityFactory(this.playerEntityController);

        entitiesFactory.displayEntityTypes();
        while( !selection ) {
            String input[] = inputReader.nextLine().split("\s", 2);
            if(!isEntity() && input.length != 2) {
                System.out.println("Provide name for player!");
            }
            else {
                String entityName = input.length == 2 ? input[1] : entity.getName();
                Entity entity = entitiesFactory.createEntity(input[0], entityName, null);
                if(entity != null) {
                    setNewEntity(entity);
                    selection = true;
                }
                else {
                    System.out.println("Please type valid character class");
                }
            }

        }
        CommandExecutor commandExecutor = new CommandExecutor();
        SelectionCharacterCommand selectionCharacterCommand = new SelectionCharacterCommand(this.entity, this.newEntity);
        commandExecutor.executeCommand(selectionCharacterCommand);
        this.entity = selectionCharacterCommand.getEntity();
        return this;
    }

    private void setNewEntity(Entity entity) {
        this.newEntity = entity;
    }

    private Entity getEntity() {
        return entity;
    }

    private boolean isEntity() {
        return this.entity != null;
    }
}
