package com.game.controllers.decorator;

import com.game.controllers.AbstractEntityController;
import com.game.entities.Entity;
import com.game.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class TrainingDummy implements AbstractEntityController {

    private final AbstractEntityController wrappee;
    private final Random random;

    public TrainingDummy(AbstractEntityController source){
        wrappee = source;
        random = new Random();
    }

    @Override
    public Optional<Entity> getNextTarget(Entity entity, List<Entity> allFriends, List<Entity> allEnemies) {

        Optional<Entity> target = wrappee.getNextTarget(entity, allFriends, allEnemies);

        //make the right decision 50% of time
        if(random.nextDouble(1.0) < 0.5) {
            return target;
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> getRealPlayerEntityOwner() {
        return Optional.empty();
    }
}
