package com.game.battle;

import com.game.Names;
import com.game.controllers.NPC;
import com.game.entities.Archer;
import com.game.entities.Entity;
import com.game.entities.Warrior;
import com.game.entities.Wizard;

import java.util.ArrayList;
import java.util.List;

public class BattleBuilder {
    private List<Entity> friends;
    private List<Entity> enemies;
    private Names names;

    public BattleBuilder(){
        resetBuilder();
    }

    public void resetBuilder(){
        friends = new ArrayList<>();
        enemies = new ArrayList<>();
        names = new Names();
    }

    public void resetEntities(){
        friends.forEach(Entity::revive);
        enemies.forEach(Entity::revive);
    }

    public void addFriend(Entity entity){
        friends.add(entity);
    }
    public void addEnemy(Entity entity){
        enemies.add(entity);
    }

    public Entity getWarrior(int level){
        return new Warrior(names.random(), level, new NPC());
    }

    public Entity getWizard(int level){
        return new Wizard(names.random(), level, new NPC());
    }

    public Entity getArcher(int level){
        return new Archer(names.random(), level, new NPC());
    }

    public List<Entity> getFriends(){
        return friends;
    }

    public List<Entity> getEnemies(){
        return friends;
    }

    public String getRandomName(){
        return names.random();
    }

    public Battle createBattle(){
        return new Battle(friends, enemies);
    }
}
