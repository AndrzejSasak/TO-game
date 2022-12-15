package com.game.sharedUserInterface;

import com.game.Command.CommandExecutor;
import com.game.Command.SelectionCharacterCommand;
import com.game.controllers.PlayerEntityController;
import com.game.entities.Archer;
import com.game.entities.Entity;
import com.game.entities.Warrior;
import com.game.entities.Wizard;

import java.util.Scanner;

public class SelectEntityType {
    private Entity entity;
    private Entity newEntity;
    private PlayerEntityController playerEntityControllerController;

    public Entity createNewEntity(Entity entity, PlayerEntityController playerEntityControllerController) {
        return this.setEntity(entity)
                .setPlayerController(playerEntityControllerController)
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
        this.playerEntityControllerController = playerEntityControllerController;
        return this;
    }

    SelectEntityType setEntity(Entity entity) {
        this.entity = entity;
        return this;
    }

    SelectEntityType selectPlayerEntity() {
        boolean selection = false;
        Scanner inputReader = new Scanner(System.in);
        displayEntityTypes();
        while( !selection ) {
            String input[] = inputReader.nextLine().split("\s");
            if(!isEntity() && input.length != 2) {
                System.out.println("Provide name for player!");
            }
            else {
                switch (input[0]) {
                    case "1":
                        setNewEntity(new Archer(isEntity() ? entity.getName() : input[1], playerEntityControllerController));
                        selection = true;
                        break;
                    case "2":
                        setNewEntity(new Warrior(isEntity() ? entity.getName() : input[1], playerEntityControllerController));
                        selection = true;
                        break;
                    case "3":
                        setNewEntity(new Wizard(isEntity() ? entity.getName() : input[1], playerEntityControllerController));
                        selection = true;
                        break;
                    default:
                        System.out.println("Type valid option(number)");
                        break;
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

    private void displayEntityTypes() {
        System.out.println("Select character class and player name (add space between):");
        System.out.println("1. Archer (can dodge attack)");
        System.out.println("2. Warrior (can block attack)");
        System.out.println("3. Wizard (has stronger critical attack)");
    }

    private boolean isEntity() {
        return this.entity != null;
    }
}
