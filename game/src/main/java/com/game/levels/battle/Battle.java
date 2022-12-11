package com.game.levels.battle;

import com.game.entities.Entity;

import java.util.List;

public class Battle {
    BattleIterator iterator;
    private final List<Entity> teamOne;
    private final List<Entity> teamTwo;

    public Battle(List<Entity> teamOne, List<Entity> teamTwo){
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        iterator = new BattleIterator(teamOne, teamTwo);
    }

    //returns the winning team
    public List<Entity> play(){
        while (iterator.hasNext()){
            Entity entity = iterator.getNext();
            if (teamOne.contains(entity)) {
                entity.update(teamOne, teamTwo);
            } else {
                entity.update(teamTwo, teamOne);
            }
        }
        if(teamOne.stream().allMatch(Entity::isDead) && teamTwo.stream().allMatch(Entity::isDead))
            return null; //We have a draw!
        if(teamOne.stream().allMatch(Entity::isDead))
            return teamTwo;
        return teamOne;
    }
}
