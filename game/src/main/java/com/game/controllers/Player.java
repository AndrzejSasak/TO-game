package com.game.controllers;

import com.game.entities.Entity;

import java.util.List;
import java.util.Scanner;

public class Player implements EntityController{
    @Override
    public Entity getNextTarget(Entity entity, List<Entity> allFriends, List<Entity> allEnemies) {
        //TODO command

        for (int i = 0; i < allFriends.size(); i++){
            Entity target = allFriends.get(i);
            System.out.println(target.getName() + " (" + target.getHp() + "/" + target.getMaxHp() + ")");
        }
        for (int i = 0; i < allEnemies.size(); i++){
            Entity target = allEnemies.get(i);
            System.out.println(i + " " + target.getName() + " (" + target.getHp() + "/" + target.getMaxHp() + ")");
        }
        Scanner myInput = new Scanner( System.in );
        int a = myInput.nextInt();
        if(a >= allEnemies.size() || a < 0)
            return null;
        return allEnemies.get(a);
    }
}
