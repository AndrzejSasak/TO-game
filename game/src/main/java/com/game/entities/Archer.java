package com.game.entities;

import com.game.Messages;
import com.game.controllers.EntityController;

import java.util.List;
import java.util.stream.Collectors;

public class Archer extends Entity {
    public Archer(String name, EntityController controller){
        super(name, controller);
        this.maxHp = 350;
        this.attack = 20;
        this.professionName = "Archer";
        this.hp = maxHp;
    }

    private int buffedAttack(Entity enemy){
        if(enemy instanceof Wizard)
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

        if (!(attacker instanceof Wizard) && rand.nextDouble(1.) < 0.15){
            Messages.dodgeMessage(this);
            if (rand.nextDouble(1.) < 0.5){
                Messages.counterattackMessage(this, attacker);
                attacker.getHit(attack, this, allEnemies, allFriends);
            }
            return;
        }

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
        return allEnemies.stream().filter(entity -> entity instanceof Wizard && !entity.isDead()).collect(Collectors.toList());
    }
}
