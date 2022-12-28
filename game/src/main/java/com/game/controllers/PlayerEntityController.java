package com.game.controllers;

import com.game.Command.CommandExecutor;
import com.game.Command.Target;
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



    @Override
    public Optional<Entity> getNextTarget(Entity entity, List<Entity> allFriends, List<Entity> allEnemies) {
        Target target = null;
        LocalMessages.displayVerticalLine();
        System.out.println("Your turn!\nYour team:");
        allFriends.forEach(friend -> System.out.println(friend.getNameInfo()));
        printPossibleOptions(allEnemies.stream().filter(ent -> !ent.isDead()).toList());
        LocalMessages.displayVerticalLine();
        CommandExecutor commandExecutor = new CommandExecutor();

        while(true) {
            Scanner myInput = new Scanner(System.in);
            int chosenEnemy = myInput.nextInt();


            TargetSelectionCommand targetSelectionCommand = new TargetSelectionCommand(chosenEnemy, allEnemies.stream().filter(ent -> !ent.isDead()).toList());
            commandExecutor.executeCommand(targetSelectionCommand);
            if(targetSelectionCommand.getSelectedTarget().getEntity() != null ||
               targetSelectionCommand.getSelectedTarget().isSkipCommand()) {
                target = targetSelectionCommand.getSelectedTarget();
                break;
            }
            System.out.println("Type valid number");
        }

        return target.isSkipCommand() ? Optional.empty() : Optional.of(target.getEntity());
    }

    @Override
    public Optional<User> getRealPlayerEntityOwner() {
        return Optional.of(this.entityOwner);
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
