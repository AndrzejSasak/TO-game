package com.game.controllers;

import com.game.entities.Entity;
import com.game.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class NPCEntityController implements AbstractEntityController {

    private Random rand;

    public NPCEntityController(){
        rand = new Random();
    }

    @Override
    public Optional<Entity> getNextTarget(Entity entity, List<Entity> allFriends, List<Entity> allEnemies) {

        //skip round to load critical attack
        double hpp = (double) entity.getHp() / (double) entity.getMaxHp();
        if(!entity.haveCritical() && hpp > 0.4 && gotCriticalAttack()) {
            return Optional.empty();
        }

        List<Entity> targets = entity.getPreferredTargets(allEnemies);
        Entity finalTarget;
        
        if(!targets.isEmpty()) {
            finalTarget = targets.get(rand.nextInt(targets.size()));
        } else {
            List<Entity> list = allEnemies.stream().filter(entity1 -> !entity1.isDead()).toList();
            finalTarget = list.get(rand.nextInt(list.size()));
        }
        
        return Optional.of(finalTarget);
    }

    @Override
    public Optional<User> getRealPlayerEntityOwner() {
        return Optional.empty();
    }

    private boolean gotCriticalAttack() {
        return rand.nextDouble(1.0) < 0.3;
    }
}
