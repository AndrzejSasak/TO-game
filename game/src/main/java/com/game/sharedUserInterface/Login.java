package com.game.sharedUserInterface;

import com.game.controllers.Player;
import com.game.entities.Entity;

import java.io.InputStream;
import java.util.Scanner;

public class Login {
    Entity entity;
    Player playerController = new Player();


    public Entity createPlayer() {
        Scanner inputReader = new Scanner(System.in);
        System.out.print("Type player login: ");
        String login = inputReader.nextLine();
        playerController.setLogin(login);
        entity = new SelectEntityType().createNewEntity(entity, playerController);
        return entity;
    }
}
