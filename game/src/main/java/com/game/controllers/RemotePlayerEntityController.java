package com.game.controllers;

import com.game.Command.AttackCommand;
import com.game.Command.CommandExecutor;
import com.game.Command.TargetSelectionCommand;
import com.game.Command.WaitCommand;
import com.game.entities.Entity;
import com.game.entities.User;
import com.game.sharedUserInterface.LocalMessages;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class RemotePlayerEntityController implements AbstractEntityController{

    private User entityOwner;

    public RemotePlayerEntityController(User entityOwner) {
        this.entityOwner = entityOwner;
    }

    @Override
    public Optional<User> getRealPlayerEntityOwner() {
        return Optional.of(this.entityOwner);
    }

    @Override
    public Optional<Entity> getNextTarget(Entity entity, List<Entity> allFriends, List<Entity> allEnemies) {
        return Optional.empty();
    }

    public void performMultiplayerAction(Entity parent) {
        displayMessages(parent, true);

        Scanner in = new Scanner(System.in);
        int chosenOption = in.nextInt();

        while ((parent.haveCritical() && chosenOption != 1) && (chosenOption != 1 || chosenOption != 0)) {
            displayMessages(parent, false);
            chosenOption = in.nextInt();
        }

        if (chosenOption == 1) {
            attack(parent);
        }
        else {
            wait(parent);
        }
    }

    private void attack(Entity parent){
        CommandExecutor commandExecutor = new CommandExecutor();
        AttackCommand attackCommand = new AttackCommand(parent);
        commandExecutor.executeCommand(attackCommand);
    }

    private void wait(Entity parent){
        CommandExecutor commandExecutor = new CommandExecutor();
        WaitCommand waitCommand = new WaitCommand(parent);
        commandExecutor.executeCommand(waitCommand);
    }

    private void displayMessages(Entity parent, boolean bFirst){
        LocalMessages.displayVerticalLine();

        if(bFirst) {
            System.out.println("Your tour!");
        }
        else {
            System.out.println("Provide valid input!");
        }

        if(parent.haveCritical()) {
            System.out.println("You have acquired critical attack! Press 1 to use it!");
        }
        else {
            System.out.println("Press 1 to attack or 0 to wait and acquire boost for next round!");
        }

        LocalMessages.displayVerticalLine();
    }
}
