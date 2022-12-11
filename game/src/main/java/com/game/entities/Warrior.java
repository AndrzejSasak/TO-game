package com.game.entities;


import com.game.Messages;
import com.game.controllers.EntityController;

import java.util.List;
import java.util.stream.Collectors;

public class Warrior extends Entity {


    public Warrior(String name, EntityController controller){
        super(name, controller);
        this.maxHp = 350;
        this.attack = 20;
        this.professionName = "Warrior";
        this.hp = maxHp;
    }

    private int buffedAttack(Entity enemy){
        if(enemy instanceof Archer)
            return (int) (attack * 1.2);
        return attack;
    }

    @Override
    public void attack(Entity target, List<Entity> allFriends, List<Entity> allEnemies) {
        Messages.attackMessage(this, target);
        target.getHit(buffedAttack(target), this, allEnemies, allFriends);
    }

    @Override
    public void getHit(int attackPoints, Entity attacker, List<Entity> allFriends, List<Entity> allEnemies) {
        hp -= attackPoints;
        Messages.hitMessage(this, attackPoints);
        if(hp < 0){
            alive = false;
            Messages.deathMessage(this);
        }
    }

    @Override
    public List<Entity> getPreferredTargets(List<Entity> allEnemies) {
        return allEnemies.stream().filter(entity -> entity instanceof Archer).collect(Collectors.toList());
    }

    @Override
    public int getMaxHp() {
        return maxHp;
    }

    @Override
    public String getProfessionName() {
        return professionName;
    }
}
