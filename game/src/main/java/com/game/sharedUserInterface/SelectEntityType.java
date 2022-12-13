package com.game.sharedUserInterface;

import com.game.Command.CommandExecutor;
import com.game.Command.SelectionCharacterCommand;
import com.game.controllers.Player;
import com.game.entities.Archer;
import com.game.entities.Entity;
import com.game.entities.Warrior;
import com.game.entities.Wizard;

import java.util.Scanner;

public class SelectEntityType {
    private Entity entity;
    private Entity newEntity;

    SelectEntityType(Entity entity) {
        this.entity = entity;
    }

    public void selectPlayerEntity(Player playerController) {
        boolean selection = false;
        Scanner inputReader = new Scanner(System.in);
        displayEntityTypes();
        while( !selection ) {
            String input = inputReader.nextLine();
            switch (input) {
                case "1":
                    setNewEntity(new Archer(entity.getName(), playerController));
                    selection = true;
                    break;
                case "2":
                    setNewEntity(new Warrior(entity.getName(), playerController));
                    selection = true;
                    break;
                case "3":
                    setNewEntity(new Wizard(entity.getName(), playerController));
                    selection = true;
                    break;
                default:
                    System.out.println("Type valid option(number)");
                    break;
            }
        }
        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.executeCommand(new SelectionCharacterCommand(this.entity, this.newEntity));
    }

    private void setNewEntity(Entity entity) {
        this.newEntity = entity;
    }

    private void displayEntityTypes() {
        System.out.println("1. Archer (can dodge attack)");
        System.out.println("2. Warrior (can block attack)");
        System.out.println("3. Wizard (has stronger critical attack)");
    }
}
