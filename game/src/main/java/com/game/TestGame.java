package com.game;

import com.game.controllers.NPC;
import com.game.entities.Archer;
import com.game.entities.Entity;
import com.game.entities.Warrior;
import com.game.entities.Wizard;
import com.game.battle.Battle;

import java.util.ArrayList;
import java.util.List;

public class TestGame {
    private final List<Entity> myTeam;
    private final List<Entity> enemies;
    public TestGame(){
        myTeam = new ArrayList<>();
        enemies = new ArrayList<>();
        myTeam.add(new Wizard("Adrian", new NPC()));
        myTeam.add(new Warrior("Tomek", new NPC()));
        enemies.add(new Archer("Konrad", new NPC()));
        enemies.add(new Warrior("Andrzej", new NPC()));
    }
    public void play(){
        Battle battle = new Battle(myTeam, enemies);
        List<Entity> winner = battle.play();

        if (winner == null)
            System.out.println("Draw!");
        else if (winner.equals(myTeam))
            System.out.println("Win!");
        else
            System.out.println("Defeat!");
    }
}
