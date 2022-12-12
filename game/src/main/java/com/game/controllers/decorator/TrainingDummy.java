package com.game.controllers.decorator;

import com.game.controllers.EntityController;
import com.game.entities.Entity;

import java.util.List;
import java.util.Random;

public class TrainingDummy implements EntityController {
    //Allows the entity to make the right decision only 50% of the time
    private final EntityController wrappee;
    private final Random random;

    public TrainingDummy(EntityController source){
        wrappee = source;
        random = new Random();
    }
    @Override
    public Entity getNextTarget(Entity entity, List<Entity> allFriends, List<Entity> allEnemies) {
        Entity target = wrappee.getNextTarget(entity, allFriends, allEnemies);
        if(random.nextDouble(1.) < 0.5)
            return target;
        return null;
    }
}
