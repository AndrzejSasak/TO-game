package com.game.entitiesFactories;

import com.game.controllers.AbstractEntityController;
import com.game.entities.Archer;
import com.game.entities.Entity;
import com.game.entities.Warrior;
import com.game.entities.Wizard;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class EntitiesFactory {
    protected AbstractEntityController entityController;
    protected Map<String, Supplier<Entity>> creators = new HashMap<>();
    protected String name;
    protected int level;

    public EntitiesFactory(AbstractEntityController entityController) {
        this.entityController = entityController;
        creators.put("archer", this::createArcher);
        creators.put("warrior", this::createWarrior);
        creators.put("wizard", this::createWizard);
    }

    public abstract Entity createEntity(String optionNumber, String name, Optional<Integer> level);

    abstract Entity createArcher();
    abstract Entity createWarrior();
    abstract Entity createWizard();
}
