package com.game.controllers;

import com.game.Command.CommandExecutor;
import com.game.Command.TargetSelectionCommand;
import com.game.entities.Entity;
import com.game.entities.User;
import com.game.sharedUserInterface.LocalMessages;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PlayerEntityController implements AbstractEntityController {

    private User entityOwner;

    public PlayerEntityController(User entityOwner) {
        this.entityOwner = entityOwner;
    }

    public User getEntityOwner() {
        return entityOwner;
    }

    @Override
    public Optional<Entity> getNextTarget(Entity entity, List<Entity> allFriends, List<Entity> allEnemies) {

        LocalMessages.displayVerticalLine();
        System.out.println("Your turn!\nYour team:");
        allFriends.forEach(friend -> System.out.println(friend.getNameInfo()));
        printPossibleOptions(allEnemies);
        LocalMessages.displayVerticalLine();

        Scanner myInput = new Scanner(System.in);
        int chosenEnemy = myInput.nextInt();

        CommandExecutor commandExecutor = new CommandExecutor();
        TargetSelectionCommand targetSelectionCommand = new TargetSelectionCommand(chosenEnemy, allEnemies);
        commandExecutor.executeCommand(targetSelectionCommand);

        return targetSelectionCommand.getSelectedTarget();
    }

    private void printPossibleOptions(List<Entity> allEnemies) {
        System.out.println("Choose option:");
        int i;
        for (i = 0; i < allEnemies.size(); i++){
            Entity target = allEnemies.get(i);
            System.out.println(i + ". Attack: " + target.getNameInfo());
        }
        System.out.println(i + ". Skip turn.");
    }

}
