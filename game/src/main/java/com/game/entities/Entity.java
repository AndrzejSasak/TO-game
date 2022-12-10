package com.game.entities;

import com.game.Messages;
import com.game.controllers.EntityController;

import java.util.List;
import java.util.Random;

public abstract class Entity {
    protected int hp;
    protected boolean alive;
    protected Random rand;
    protected String name;
    EntityController controller;

    protected Entity(String name, EntityController controller){
        this.controller = controller;
        this.name = name;
        alive = true;
        rand = new Random();
    }

    public void update(List<Entity> allFriends, List<Entity> allEnemies){
        Entity target = controller.getNextTarget(this, allFriends, allEnemies);
        if (target != null) {
            attack(target, allFriends, allEnemies);
        }
        else
            Messages.passMessage(this);
    }

    protected abstract void attack(Entity target, List<Entity> allFriends, List<Entity> allEnemies);
    public abstract void getHit(int attackPoints, Entity attacker, List<Entity> allFriends, List<Entity> allEnemies);
    public abstract List<Entity> getPreferredTargets(List<Entity> allEnemies);
    public abstract int getMaxHp();
    public abstract String getProfessionName();

    public boolean isDead(){
        return !alive;
    }
    public int getHp() {
        return hp;
    }
    public String getName(){
        return name;
    }
}
