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
        Names names = new Names();
        myTeam.add(new Wizard("Adrian",1, new NPC()));
        myTeam.add(new Warrior(names.random(), 3,new NPC()));
        enemies.add(new Archer(names.random(), 2, new NPC()));
        enemies.add(new Warrior(names.random(), 3, new NPC()));
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
