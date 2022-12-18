package com.game.entities;

import com.game.Messages;
import com.game.controllers.AbstractEntityController;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public abstract class Entity implements Serializable {

    protected int maxHp;
    protected int attackPoints;
    protected int level;
    protected int hp;
    protected boolean hasBoost;
    protected boolean isAlive;
    protected Random rand;
    protected String name;
    protected String professionName;
    protected AbstractEntityController controller;

    //Multiplayer actions control
    public boolean bWantsToAttack = false;
    public boolean bWantsToWait = false;

    protected Entity(String name, AbstractEntityController controller) {
        this.controller = controller;
        this.name = name;
        isAlive = true;
        hasBoost = false;
        rand = new Random();
    }

    public void update(List<Entity> allFriends, List<Entity> allEnemies){
        Optional<Entity> target = controller.getNextTarget(this, allFriends, allEnemies);

        if(target.isPresent()) {
            attack(target.get(), allFriends, allEnemies);
            hasBoost = false;
        } else {
            Messages.passMessage(this);
            if(rand.nextDouble(1.0) < 0.9) {
                hasBoost = true;
            }
        }

    }

    public void multiplayerAttack(Entity target){
        attack(target, null, null);
    }

    protected void attack(Entity target, List<Entity> allFriends, List<Entity> allEnemies) {
        int attack = criticalAttack(this.attackPoints);
        attack = boostedAttack(attack, target);
        Messages.attackMessage(this, target);
        target.getHit(attack, this, allEnemies, allFriends);
    }

    protected int criticalAttack(int attack){
        if (hasBoost) {
            Messages.criticalAttackMessage();
            attack = (int) (attack * 2.1);
        }

        return attack;
    }

    protected int boostedAttack(int attack, Entity enemy){
        return attack;
    }

    protected abstract void init(int level);

    protected boolean dodge(Entity attacker) {
        boolean dodged = rand.nextDouble(1.) < 0.15;

        if (!dodged && hasBoost) {
            dodged = rand.nextDouble(1.) < 0.20;
        }

        return dodged;
    }

    protected int countDamage(int attackPoints, Entity attacker){
        return attackPoints;
    }

    public void getHit(int attackPoints, Entity attacker, List<Entity> allFriends, List<Entity> allEnemies){
        if (!isAlive) {
            return;
        }

        if (dodge(attacker)){
            Messages.dodgeMessage(this);
            if (rand.nextDouble(1.) < 0.5) {
                Messages.counterattackMessage(this, attacker);
                attacker.getHit(this.attackPoints, this, allEnemies, allFriends);
            }
            return;
        }

        int damage = countDamage(attackPoints, attacker);
        hp -= damage;
        if(hp < 1){
            hp = 0;
            isAlive = false;
        }

        Messages.hitMessage(this, attackPoints);
        if(hp == 0) {
            Messages.deathMessage(this);
        }

    }

    public abstract List<Entity> getPreferredTargets(List<Entity> allEnemies);

    public String getProfessionName() {
        return professionName;
    }

    public void setLevel(int level){
        init(level);
    }

    public void revive() {
        init(level); isAlive = true;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public boolean isDead(){
        return !isAlive;
    }

    public int getHp() {
        return hp;
    }

    public boolean haveCritical(){
        return hasBoost;
    }

    public void setCritical(boolean shouldHaveBoost) {this.hasBoost = shouldHaveBoost; }

    public int getMaxHp() {return maxHp;}

    public String getName(){
        return name;
    }

    public AbstractEntityController getController() { return controller;}

    public void setController(AbstractEntityController newController) {this.controller = null; this.controller = newController;}

    public String getNameInfo() {
        if (isDead())
            return getProfessionName() + " " + name + " [" + level + "]" + " (Dead)";
        return getProfessionName() + " " + name + " [" + level + "]" + " ("+ hp + "/" + getMaxHp() + ")";
    }
}
