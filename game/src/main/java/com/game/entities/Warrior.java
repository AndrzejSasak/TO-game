package com.game.entities;


import com.game.controllers.AbstractEntityController;

import java.util.List;
import java.util.stream.Collectors;

public class Warrior extends Entity {

    public Warrior(String name, AbstractEntityController controller){
        super(name, controller);
        this.professionName = "Archer";
        init(1);
    }

    public Warrior(String name, int level , AbstractEntityController controller){
        super(name, controller);
        this.professionName = "Warrior";
        init(level);
    }

    @Override
    protected void init(int level){
        this.level=level;
        this.maxHp = (int) (350 * (1. + ((level - 1) * 0.12)));
        this.attackPoints = (int) (20 * (1. + ((level - 1) * 0.12)));
        this.hp = maxHp;
    }

    @Override
    protected int boostedAttack(int attack, Entity enemy) {
        if(enemy instanceof Archer) {
            return (int) (attack * 1.2);
        }

        return attack;
    }

    @Override
    protected int countDamage(int attackPoints, Entity attacker) {
        int damage = attackPoints;
        if (attacker instanceof Archer) {
            damage = (int) (attackPoints * 0.9);
        }

        return damage;
    }

    @Override
    public List<Entity> getPreferredTargets(List<Entity> allEnemies) {
        return allEnemies.stream()
                .filter(entity -> entity instanceof Archer && !entity.isDead())
                .toList();
    }
}
