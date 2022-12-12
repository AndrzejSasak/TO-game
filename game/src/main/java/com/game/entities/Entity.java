package com.game.entities;

import com.game.Messages;
import com.game.controllers.EntityController;

import java.util.List;
import java.util.Random;

public abstract class Entity {
    protected int maxHp;
    protected int attack;
    protected int level;
    protected String professionName;
    protected int hp;
    protected boolean boost;
    protected boolean alive;
    protected Random rand;
    protected String name;
    protected EntityController controller;
    protected Entity(String name, EntityController controller){
        this.controller = controller;
        this.name = name;
        alive = true;
        boost = false;
        rand = new Random();
    }

    public void update(List<Entity> allFriends, List<Entity> allEnemies){
        Entity target = controller.getNextTarget(this, allFriends, allEnemies);
        if (target != null){
            attack(target, allFriends, allEnemies);
            boost = false;
        }
        else{
            Messages.passMessage(this);
            if(rand.nextDouble(1.) < 0.9)
                boost = true;
        }
    }

    protected int criticalAttack(int attack){
        if (boost){
            Messages.criticalAttackMessage();
            attack = (int) (attack * 2.1);
        }
        return attack;
    }

    protected int boostedAttack(int attack, Entity enemy){
        return attack;
    }

    protected abstract void init(int level);

    protected void attack(Entity target, List<Entity> allFriends, List<Entity> allEnemies){
        int attack = criticalAttack(this.attack);
        attack = boostedAttack(attack, target);
        Messages.attackMessage(this, target);
        target.getHit(attack, this, allEnemies, allFriends);
    }

    protected boolean dodge(Entity attacker){
        boolean dodge = rand.nextDouble(1.) < 0.15;
        if (!dodge && boost)
            dodge = rand.nextDouble(1.) < 0.20;
        return dodge;
    }

    protected int countDamage(int attackPoints, Entity attacker){
        return attackPoints;
    }

    public void getHit(int attackPoints, Entity attacker, List<Entity> allFriends, List<Entity> allEnemies){
        if (!alive)
            return;
        boolean dodge = dodge(attacker);
        if (dodge){
            Messages.dodgeMessage(this);
            if (rand.nextDouble(1.) < 0.5){
                Messages.counterattackMessage(this, attacker);
                attacker.getHit(attack, this, allEnemies, allFriends);
            }
            return;
        }
        int damage = countDamage(attackPoints, attacker);
        hp -= damage;
        if(hp < 1){
            hp = 0;
            alive = false;
        }
        Messages.hitMessage(this, attackPoints);
        if(hp == 0)
            Messages.deathMessage(this);
    }

    public abstract List<Entity> getPreferredTargets(List<Entity> allEnemies);

    public String getProfessionName() {return professionName;};

    public void setLevel(int level){init(level);}

    public void revive() {init(level); alive = true;}

    //TODO change name to getAttackPoints
    public int getAttack() {return attack;}

    public boolean isDead(){
        return !alive;
    }

    public int getHp() {
        return hp;
    }

    public boolean haveCritical(){return boost;};

    public int getMaxHp() {return maxHp;};

    public String getName(){
        return name;
    }

    public String getNameInfo() {
        if (isDead())
            return getProfessionName() + " " + name + " [" + level + "]" + " (Dead)";
        return getProfessionName() + " " + name + " [" + level + "]" + " ("+ hp + "/" + getMaxHp() + ")";
    }
}
