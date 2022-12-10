package com.game.entities;


import com.game.Messages;
import com.game.controllers.EntityController;

import java.util.List;
import java.util.stream.Collectors;

public class Warrior extends Entity {
    private int maxHp = 350;
    private int attack = 20;
    private final String professionName = "Warrior";

    public Warrior(String name, EntityController controller){
        super(name, controller);
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
        if (!alive)
            return;

        hp -= attackPoints;
        if(hp < 1){
            hp = 0;
            alive = false;
        }

        Messages.hitMessage(this, attackPoints);
        if(hp == 0)
            Messages.deathMessage(this);
    }

    @Override
    public List<Entity> getPreferredTargets(List<Entity> allEnemies) {
        return allEnemies.stream().filter(entity -> entity instanceof Archer && !entity.isDead()).collect(Collectors.toList());
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
