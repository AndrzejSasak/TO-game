package com.game.entities;

import com.game.Messages;
import com.game.controllers.AbstractEntityController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Wizard extends Entity {
    public Wizard(String name, AbstractEntityController controller) {
        super(name, controller);
        this.professionName = "Wizard";
        init(1);
    }

    public Wizard(String name, int level , AbstractEntityController controller) {
        super(name, controller);
        this.professionName = "Wizard";
        init(level);
    }

    @Override
    protected void init(int level) {
        this.level = level;
        this.maxHp = (int) (380 * (1. + ((level - 1) * 0.12)));
        this.attackPoints = (int) (28 * (1. + ((level - 1) * 0.12)));
        this.hp = maxHp;
    }

    @Override
    public void attack(Entity target, List<Entity> allFriends, List<Entity> allEnemies) {
        int attack = criticalAttack(this.attackPoints);
        if (allEnemies.size() < 2) {
            Messages.attackMessage(this, target);
            target.getHit(attack, this, allEnemies, allFriends);
            return;
        }

        Messages.areaAttackMessage(this);
        int strongAttack = (int) (attack * (2./3.));
        int weakAttack = (int) (attack * (1./3.) / allEnemies.size());
        List<Entity> remainingEnemies = new ArrayList<>(allEnemies);
        remainingEnemies.remove(target);
        target.getHit(strongAttack, this, allEnemies, allFriends);
        remainingEnemies.forEach(entity -> entity.getHit(weakAttack, this, allEnemies, allFriends));
    }

    @Override
    protected boolean dodge(Entity attacker){
        boolean dodge = rand.nextDouble(1.) < 0.1;
        if (!dodge && hasBoost) {
            dodge = rand.nextDouble(1.) < 0.18;
        }

        return dodge;
    }


    @Override
    public List<Entity> getPreferredTargets(List<Entity> allEnemies) {
        return allEnemies.stream().filter(entity -> entity instanceof Warrior && !entity.isDead()).collect(Collectors.toList());
    }
}
