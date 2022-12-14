package com.game.entities;

import com.game.controllers.AbstractEntityController;

import java.util.List;
import java.util.stream.Collectors;

public class Archer extends Entity {

    public Archer(String name, AbstractEntityController controller){
        super(name, controller);
        this.professionName = "Archer";
        init(1);
    }

    public Archer(String name, int level , AbstractEntityController controller){
        super(name, controller);
        this.professionName = "Archer";
        init(level);
    }

    @Override
    protected void init(int level){
        this.level = level;
        this.maxHp = (int) (350 * (1. + ((level - 1) * 0.12)));
        this.attackPoints = (int) (20 * (1. + ((level - 1) * 0.12)));
        this.hp = maxHp;
    }

    @Override
    protected int boostedAttack(int attack, Entity enemy){
        if(enemy instanceof Wizard) {
            return (int) (attack * 1.2);
        }

        return attack;
    }


    @Override
    public List<Entity> getPreferredTargets(List<Entity> allEnemies) {
        return allEnemies.stream()
                .filter(entity -> entity instanceof Wizard && !entity.isDead())
                .toList();
    }
}
