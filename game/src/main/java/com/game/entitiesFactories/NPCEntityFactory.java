package com.game.entitiesFactories;

import com.game.controllers.AbstractEntityController;
import com.game.controllers.NPCEntityController;
import com.game.entities.Archer;
import com.game.entities.Entity;
import com.game.entities.Warrior;
import com.game.entities.Wizard;

import java.util.Optional;
import java.util.Random;

public class NPCEntityFactory extends EntitiesFactory{
    public NPCEntityFactory(AbstractEntityController entityController) {
        super(entityController);
    }

    @Override
    public Entity createEntity(String optionNumber, String name, Optional<Integer> level) {
        optionNumber = optionNumber.toLowerCase();
        if(!creators.containsKey(optionNumber))
            return null;
        if(entityController instanceof NPCEntityController) {
            ((NPCEntityController) entityController).setRand(new Random());
        }
        this.name = name;
        this.level = level.orElse(1);
        return creators.get(optionNumber).get();
    }

    @Override
    Entity createArcher() {
        return new Archer(name, level, entityController);
    }

    @Override
    Entity createWarrior() {
        return new Warrior(name, level, entityController);
    }

    @Override
    Entity createWizard() {
        return new Wizard(name, level, entityController);
    }
}
