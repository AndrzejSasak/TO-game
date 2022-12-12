package com.game.levels;

import com.game.Names;
import com.game.battle.Battle;
import com.game.battle.BattleBuilder;
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
    private final String playerName;
    public Training(String playerName){
        this.playerName = playerName;
    }

    @Override
    public boolean run() {
        BattleBuilder builder = new BattleBuilder();

        System.out.println("Welcome to Projekt IO training level.");
        System.out.println("Here you will learn basic combat skills.\n");

        String first_opponent_name = builder.getRandomName();

        System.out.println("In the first battle you will stand against dark warlord " + first_opponent_name + "!");
        System.out.println("You can attack him right away choosing the correct option\nor skip your turn in order to maximalize your chances of dealing critical damage\nand parring your opponents attack!" );

        builder.addFriend(new Warrior(playerName, 1, new Player()));
        builder.addEnemy(new Archer(first_opponent_name, 1, new TrainingDummy(new NPC())));

        while (true){
            Battle battle = builder.createBattle();
            List<Entity> winner = battle.play();
            if (winner.equals(builder.getFriends())){
                System.out.println("Congratulations on winning your first battle!");
                break;
            }
            else {
                builder.resetEntities();
                System.out.println("Try again!");
            }
        }


        System.out.println("Some characters have special abilities like area attack that hurts more than one enemy!");
        System.out.println("Character level can affect its abilities, try to decide if you want to attack weaker or stronger enemies first!");
        System.out.println("In this battle you will face multiple enemies!");

        builder.resetBuilder();
        builder.addFriend(new Wizard(playerName, 20, new Player()));
        builder.addEnemy(builder.getArcher(1));
        builder.addEnemy(builder.getArcher(1));
        builder.addEnemy(builder.getArcher(4));
        builder.addEnemy(builder.getArcher(1));

        while (true){
            Battle battle = builder.createBattle();
            List<Entity> winner = battle.play();
            if (winner.equals(builder.getFriends())){
                System.out.println("Congratulations on completing your training!");
                break;
            }
            else {
                builder.resetEntities();
                System.out.println("Try again!");
            }
        }
        return true;
    }
}
