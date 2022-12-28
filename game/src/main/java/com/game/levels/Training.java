package com.game.levels;

import com.game.Names;
import com.game.battle.Battle;
import com.game.controllers.NPCEntityController;
import com.game.controllers.PlayerEntityController;
import com.game.controllers.decorator.TrainingDummy;
import com.game.entities.Archer;
import com.game.entities.Entity;
import com.game.entities.Warrior;
import com.game.entities.Wizard;
import com.game.entitiesFactories.NPCEntityFactory;
import com.game.entitiesFactories.PlayerEntityFactory;

import java.util.List;
import java.util.Optional;

public class Training implements Level {

    private final String playerName;
    private final Names names;
    private NPCEntityFactory npcEntityFactory;
    private NPCEntityFactory entityFactory = new NPCEntityFactory(new PlayerEntityController(null));

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
        npcEntityFactory = new NPCEntityFactory(new TrainingDummy(new NPCEntityController()));

        System.out.println("Welcome to Projekt TO training level.");
        System.out.println("Here you will learn basic combat skills.\n");
        System.out.println("In the first battle you will stand against dark warlord " + firstOpponentName + "!");
        System.out.println("You can attack him right away choosing the correct option\nor skip your turn in order " +
                "to maximalize your chances of dealing critical damage\nand parring your opponents attack!" );

        List<Entity> friends = List.of(entityFactory.createEntity("Warrior", playerName, Optional.of(1)));
        List<Entity> enemies
                = List.of(npcEntityFactory.createEntity("Archer", firstOpponentName, Optional.of(1)));

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
        npcEntityFactory = new NPCEntityFactory(new NPCEntityController());

        List<Entity> friends = List.of(entityFactory.createEntity("Warrior", playerName, Optional.of(20)));
        List<Entity> enemies = List.of(
                npcEntityFactory.createEntity("Archer", names.getRandomName(), Optional.of(1)),
                npcEntityFactory.createEntity("Archer", names.getRandomName(), Optional.of(1)),
                npcEntityFactory.createEntity("Archer", names.getRandomName(), Optional.of(4)),
                npcEntityFactory.createEntity("Archer", names.getRandomName(), Optional.of(1))
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

            if (winner.equals(battle.getTeamOne())) {
                break;
            } else {
                battle.getTeamOne().forEach(Entity::revive);
                battle.getTeamTwo().forEach(Entity::revive);
                System.out.println("Try again!");
            }
        }
    }
}
