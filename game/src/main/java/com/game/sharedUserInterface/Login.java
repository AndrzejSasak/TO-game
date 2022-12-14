package com.game.sharedUserInterface;

import com.game.controllers.PlayerEntityController;
import com.game.entities.Entity;

import java.util.Scanner;

public class Login {
    Entity entity;
    PlayerEntityController playerEntityControllerController = new PlayerEntityController();


    public Entity createPlayer() {
        Scanner inputReader = new Scanner(System.in);
        System.out.print("Type player login: ");
        String login = inputReader.nextLine();
        playerEntityControllerController.setLogin(login);
        entity = new SelectEntityType().createNewEntity(entity, playerEntityControllerController);
        return entity;
    }
}
