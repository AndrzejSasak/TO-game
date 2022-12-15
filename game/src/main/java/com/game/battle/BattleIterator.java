package com.game.battle;

import com.game.entities.Entity;

import java.util.List;
import java.util.stream.Stream;

public class BattleIterator {

    private final Battle battle;
    private int index;

    public BattleIterator(Battle battle){
        this.battle = battle;
        index = 0;
    }

    public boolean hasNext(){
        return !battle.getTeamOne().stream().allMatch(Entity::isDead)
                && !battle.getTeamTwo().stream().allMatch(Entity::isDead);
    }

    public Entity getNext() {
        if (!hasNext()) {
            return null;
        }

        List<Entity> allEntitiesInBattle
                = Stream.concat(battle.getTeamOne().stream(), battle.getTeamTwo().stream()).toList();

        return getAliveEntity(allEntitiesInBattle);
    }



    private Entity getAliveEntity(List<Entity> allEntitiesInBattle) {
        while(true) {
            Entity entity = allEntitiesInBattle.get(index);
            index += 1;
            index %= allEntitiesInBattle.size();
            if (!entity.isDead()) {
                return entity;
            }
        }
    }
}
