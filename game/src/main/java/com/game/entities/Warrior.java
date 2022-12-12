package com.game.entities;


import com.game.Messages;
import com.game.controllers.EntityController;

import java.util.List;
import java.util.stream.Collectors;

public class Warrior extends Entity {
    public Warrior(String name, int level ,EntityController controller){
        super(name, controller);
        this.professionName = "Warrior";
        init(level);
    }
    @Override
    protected void init(int level){
        this.level=level;
        this.maxHp = (int) (350 * (1. + ((level - 1) * 0.12)));
        this.attack = (int) (20 * (1. + ((level - 1) * 0.12)));
        this.hp = maxHp;
    }

    private int buffedAttack(Entity enemy){
        int attack = this.attack;
        if (boost){
            Messages.criticalAttackMessage();
            attack = (int) (attack * 2.1);
        }
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

        boolean dodge = rand.nextDouble(1.) < 0.15;
        if (!dodge && boost)
            dodge = rand.nextDouble(1.) < 0.20;

        if (!(attacker instanceof Wizard) && dodge){
            Messages.dodgeMessage(this);
            if (rand.nextDouble(1.) < 0.5){
                Messages.counterattackMessage(this, attacker);
                attacker.getHit(attack, this, allEnemies, allFriends);
            }
            return;
        }

        int damage = attackPoints;
        if (attacker instanceof Archer)
            damage = (int) (attackPoints * 0.9);
        hp -= damage;

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
}
