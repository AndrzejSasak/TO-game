package com.game.battle;

import com.game.entities.Entity;
import java.util.Collections;

import java.util.List;

public class Battle {
    
    private final List<Entity> teamOne;
    private final List<Entity> teamTwo;
    private BattleIterator battleIterator;



    public List<Entity> getTeamOne() {
        return teamOne;
    }

    public List<Entity> getTeamTwo() {
        return teamTwo;
    }

    public List<Entity> play() {

        battleIterator = new BattleIterator(this);
        List<Entity> teamOne = this.getTeamOne();
        List<Entity> teamTwo = this.getTeamTwo();

        while (battleIterator.hasNext()) {
            Entity entity = battleIterator.getNext();
            if (teamOne.contains(entity)) {
                entity.update(teamOne, teamTwo);
            } else {
                entity.update(teamTwo, teamOne);
            }
        }

        if(isADraw()) {
            return Collections.emptyList();
        }

        if(teamTwoWins(teamOne)) {
            return teamTwo;
        }

        return teamOne;
    }

    private boolean teamTwoWins(List<Entity> teamOne) {
        return teamOne.stream().allMatch(Entity::isDead);
    }

    private boolean isADraw() {
        return this.getTeamOne().stream().allMatch(Entity::isDead)
                && this.getTeamTwo().stream().allMatch(Entity::isDead);
    }

    public static class Builder {
        private List<Entity> teamOne;
        private List<Entity> teamTwo;

        public Builder teamOne(List<Entity> teamOne) {
            this.teamOne = teamOne;
            return this;
        }

        public Builder teamTwo(List<Entity> teamTwo) {
            this.teamTwo = teamTwo;
            return this;
        }

        public Battle build() {
            return new Battle(this);
        }
    }

    private Battle(Builder builder) {
        this.teamOne = builder.teamOne;
        this.teamTwo = builder.teamTwo;
    }


}
