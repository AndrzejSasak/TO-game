package com.game.entitiesFactories;

import com.game.controllers.AbstractEntityController;
import com.game.entities.Archer;
import com.game.entities.Entity;
import com.game.entities.Warrior;
import com.game.entities.Wizard;

import java.util.Locale;
import java.util.Optional;

public class PlayerEntityFactory extends EntitiesFactory{
    public PlayerEntityFactory(AbstractEntityController entityController) {
        super(entityController);
    }

    public void displayEntityTypes() {
        System.out.println("Select character class(by name) and player name (add space between):");
        System.out.println("1. Archer (can dodge attack)");
        System.out.println("2. Warrior (can block attack)");
        System.out.println("3. Wizard (has stronger critical attack)");
    }

    @Override
    public Entity createEntity(String optionNumber, String name, Optional<Integer> level) {
        optionNumber = optionNumber.toLowerCase();
        if(!creators.containsKey(optionNumber))
            return null;
        this.name = name;
        return creators.get(optionNumber).get();
    }

    @Override
    protected Entity createArcher(){
        return new Archer(name, entityController);
    }

    @Override
    protected Entity createWarrior(){
        return new Warrior(name, entityController);
    }

    @Override
    protected Entity createWizard(){
        return new Wizard(name, entityController);
    }
}
