package com.game;

import com.game.controllers.NPC;
import com.game.controllers.Player;
import com.game.entities.Archer;
import com.game.entities.Entity;
import com.game.entities.Warrior;

import java.util.ArrayList;
import java.util.List;

public class TestGame {
    private List<Entity> myTeam;
    private List<Entity> enemies;
    public TestGame(){
        myTeam = new ArrayList<>();
        enemies = new ArrayList<>();
        myTeam.add(new Archer("Adrian", new Player()));
        myTeam.add(new Warrior("Tomek", new NPC()));
        enemies.add(new Warrior("Konrad", new NPC()));
        enemies.add(new Warrior("Andrzej", new NPC()));
    }
    public void play(){
        List<Entity> all = new ArrayList<>(myTeam);
        all.addAll(enemies);

        while(true) {
            for (Entity entity : all) {
                if (all.stream().allMatch(Entity::isDead)) break;
                if (entity.isDead()) continue;

                if (myTeam.contains(entity)) {
                    entity.update(myTeam, enemies);
                } else {
                    entity.update(enemies, myTeam);
                }

            }
        }
    }
}
