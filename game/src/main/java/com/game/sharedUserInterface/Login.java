package com.game.sharedUserInterface;

import com.game.controllers.PlayerEntityController;
import com.game.entities.Entity;
import com.game.entities.User;

import java.util.Scanner;

public class Login {
    Entity entity;
    User user = new User();
    PlayerEntityController playerEntityController = new PlayerEntityController(user);

    public Entity createPlayer() {
        Scanner inputReader = new Scanner(System.in);
        System.out.print("Type player login: ");
        String login = inputReader.nextLine();
        user.setLogin(login);
        entity = new SelectEntityType().createNewEntity(entity, playerEntityController);
        return entity;
    }
}
