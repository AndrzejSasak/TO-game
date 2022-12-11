package com.game.levels.battle;

import com.game.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class BattleIterator {
    private List<Entity> teamOne;
    private List<Entity> teamTwo;
    private List<Entity> all;
    private int i;

    public BattleIterator(List<Entity> teamOne, List<Entity> teamTwo){
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        all = new ArrayList<>(teamOne);
        all.addAll(teamTwo);
        i = 0;
    }
    public boolean hasNext(){
        return !teamOne.stream().allMatch(Entity::isDead) && !teamTwo.stream().allMatch(Entity::isDead);
    }
    public Entity getNext(){
        if (!hasNext())
            return null;
        while(true){
            Entity entity = all.get(i);
            i += 1;
            i %= all.size();
            if (!entity.isDead()){
                return entity;
            }
        }
    }
}
