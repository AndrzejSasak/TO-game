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

import java.util.List;

public class Training implements Level {

    private final String playerName;
    private final Names names;

    public Training(String playerName){
        this.playerName = playerName;
        names = new Names();
    }

    @Override
    public boolean run() {
        battle1();
        battle2();
        return true;
    }

    private void battle1() {
        String firstOpponentName = names.getRandomName();

        System.out.println("Welcome to Projekt TO training level.");
        System.out.println("Here you will learn basic combat skills.\n");
        System.out.println("In the first battle you will stand against dark warlord " + firstOpponentName + "!");
        System.out.println("You can attack him right away choosing the correct option\nor skip your turn in order " +
                "to maximalize your chances of dealing critical damage\nand parring your opponents attack!" );

        List<Entity> friends = List.of(new Warrior(playerName, 1, new Player()));
        List<Entity> enemies = List.of(new Archer(firstOpponentName, 1, new TrainingDummy(new NPC())));

        Battle battle = new Battle.Builder()
                .teamOne(friends)
                .teamTwo(enemies)
                .build();

        carryOutBattle(battle);
        System.out.println("Congratulations on winning your first battle!");
    }

    private void battle2() {
        System.out.println("Some characters have special abilities like area attack that hurts more than one enemy!");
        System.out.println("Character level can affect its abilities, try to decide if you want to" +
                " attack weaker or stronger enemies first!");
        System.out.println("In this battle you will face multiple enemies!");

        List<Entity> friends = List.of(new Wizard(playerName, 20, new Player()));
        List<Entity> enemies = List.of(
                new Archer(names.getRandomName(), 1, new NPC()),
                new Archer(names.getRandomName(), 1, new NPC()),
                new Archer(names.getRandomName(), 4, new NPC()),
                new Archer(names.getRandomName(), 1, new NPC())
        );

        Battle battle = new Battle.Builder()
                .teamOne(friends)
                .teamTwo(enemies)
                .build();

        carryOutBattle(battle);
        System.out.println("Congratulations on completing your training!");
    }

    private void carryOutBattle(Battle battle) {
        while (true) {
            List<Entity> winner = battle.play();

            if (winner.equals(battle.getTeamTwo())) {
                break;
            } else {
                battle.getTeamOne().forEach(Entity::revive);
                battle.getTeamTwo().forEach(Entity::revive);
                System.out.println("Try again!");
            }
        }
    }
}
