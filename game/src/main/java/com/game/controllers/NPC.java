package com.game.controllers;

import com.game.entities.Entity;

import java.util.List;
import java.util.Random;

public class NPC implements EntityController {
    private Random rand;
    public NPC(){
        rand = new Random();
    }
    @Override
    public Entity getNextTarget(Entity entity, List<Entity> allFriends, List<Entity> allEnemies) {
        List<Entity> targets = entity.getPreferredTargets(allEnemies);
        Entity finalTarget;
        if(!targets.isEmpty()){
            finalTarget = targets.get(rand.nextInt(targets.size()));
        } else {
            finalTarget = allEnemies.get(rand.nextInt(allEnemies.size()));
        }
        return finalTarget;
    }
}
