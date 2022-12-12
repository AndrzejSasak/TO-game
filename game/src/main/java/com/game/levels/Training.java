package com.game.levels;

import com.game.Names;
import com.game.battle.Battle;
import com.game.controllers.NPC;
import com.game.controllers.Player;
import com.game.controllers.decorator.TrainingDummy;
import com.game.entities.Archer;
import com.game.entities.Entity;
import com.game.entities.Warrior;
import com.game.entities.Wizard;

import java.util.ArrayList;
import java.util.List;

public class Training implements Level {
    private String playerName;
    public Training(String playerName){
        this.playerName = playerName;
    }

    @Override
    public boolean run() {
        Names names = new Names();

        System.out.println("Welcome to Projekt IO training level.");
        System.out.println("Here you will learn basic combat skills.\n");

        String first_opponent_name = names.random();

        System.out.println("In the first battle you will stand against dark warlord " + first_opponent_name + "!");
        System.out.println("You can attack him right away choosing the correct option\nor skip your turn in order to maximalize your chances of dealing critical damage\nand parring your opponents attack!" );

        List<Entity> friends = new ArrayList<>();
        friends.add(new Warrior(playerName, 1, new Player()));

        List<Entity> enemies = new ArrayList<>();
        enemies.add(new Archer(first_opponent_name, 1, new TrainingDummy(new NPC())));

        Battle first_battle = new Battle(friends, enemies);

        List<Entity> winner = first_battle.play();

        if (winner.equals(friends))
            System.out.println("Congratulations on winning your first battle!");
        else {
            System.out.println("Try again!");
            return false;
        }

        System.out.println("Some characters have special abilities like area attack that hurts more than one enemy!");
        System.out.println("Character level can affect its abilities, try to decide if you want to attack weaker or stronger enemies first!");
        System.out.println("In this battle you will face multiple enemies!");

        friends = new ArrayList<>();
        friends.add(new Wizard(playerName, 20, new Player()));

        enemies = new ArrayList<>();
        enemies.add(new Archer(first_opponent_name, 1, new NPC()));
        enemies.add(new Archer(first_opponent_name, 1, new NPC()));
        enemies.add(new Warrior(first_opponent_name, 4, new NPC()));
        enemies.add(new Archer(first_opponent_name, 1, new NPC()));

        Battle second_battle = new Battle(friends, enemies);

        winner = second_battle.play();

        if (winner.equals(friends))
            System.out.println("Congratulations on completing your training!");
        else {
            System.out.println("Try again!");
            return false;
        }

        return true;
    }
}
