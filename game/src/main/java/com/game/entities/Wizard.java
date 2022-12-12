package com.game.entities;

import com.game.Messages;
import com.game.controllers.EntityController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Wizard extends Entity {
    public Wizard(String name, EntityController controller){
        super(name, controller);
        this.maxHp = 380;
        this.attack = 28;
        this.professionName = "Wizard";
        this.hp = maxHp;
    }

    @Override
    public void attack(Entity target, List<Entity> allFriends, List<Entity> allEnemies) {
        if (allEnemies.size() < 2){
            Messages.attackMessage(this, target);
            target.getHit(attack, this, allEnemies, allFriends);
            return;
        }
        Messages.areaAttackMessage(this);
        int strongAttack = (int) (attack * (2./3.));
        int weakAttack = (int) (attack * (1./3.) / allEnemies.size());
        Collection <Entity> remainingEnemies = new ArrayList<>(allEnemies);
        remainingEnemies.remove(target);
        target.getHit(strongAttack, this, allEnemies, allFriends);
        for (Entity entity : remainingEnemies) {
            entity.getHit(weakAttack, this, allEnemies, allFriends);
        }
    }

    @Override
    public void getHit(int attackPoints, Entity attacker, List<Entity> allFriends, List<Entity> allEnemies) {
        if (!alive)
            return;

        if (rand.nextDouble(1.) < 0.1){
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
        return allEnemies.stream().filter(entity -> entity instanceof Warrior && !entity.isDead()).collect(Collectors.toList());
    }
}
