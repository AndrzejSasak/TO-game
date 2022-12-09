package com.game.entities;


import com.game.Messages;
import com.game.entities.professions.Profession;

import java.util.List;
import java.util.Random;

public class NPC extends Entity{
    public NPC(Profession profession){
        super(profession, "cos");
    }

    @Override
    public void attack(List<Entity> enemies) {

    }

    @Override
    public void hit(Entity enemy) {
        int attack = (int) (profession.getAttack() * (1. + profession.getAttackBonus(enemy.profession)));
        //Messages.attackMessage(this, enemy, attack);
        enemy.getHit(attack, this);
    }

    @Override
    public void getHit(int attackPoints, Entity attacker) {
        hp -= attackPoints * (1. - profession.getProtectionBonus(attacker.profession));
        if(hp < 0)
            alive = false;
    }
}
