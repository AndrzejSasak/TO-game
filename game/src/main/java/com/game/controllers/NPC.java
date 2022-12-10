package com.game.controllers;

import com.game.entities.Entity;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
            List<Entity> list = allEnemies.stream().filter(entity1 -> !entity1.isDead()).toList();
            finalTarget = list.get(rand.nextInt(list.size()));
        }
        return finalTarget;
    }
}
