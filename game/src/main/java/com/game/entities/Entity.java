package com.game.entities;

import com.game.entities.professions.Profession;

import java.util.List;

abstract class Entity {
    protected Profession profession;
    protected int hp;
    protected boolean alive;

    protected String name;

    protected Entity(Profession profession, String name){
        this.profession = profession;
        hp = profession.getMaxHp();
        alive = true;
        this.name = name;
    }

    public abstract void attack(List<Entity> enemies);
    public abstract void hit(Entity enemy);
    public abstract void getHit(int attackPoints, Entity attacker);

    public boolean isDead(){
        return !alive;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return profession.getMaxHp();
    }

    public String getProfessionName(){
        return profession.getProfessionName();
    }

    public String getName(){
        return name;
    }
}
