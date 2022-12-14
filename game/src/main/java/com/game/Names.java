package com.game;

import java.util.*;

public class Names {
    private final List<String> allNames = List.of(
            "Ryu Hayabusa",
            "Dirk The Daring",
            "Donkey Kong",
            "The Horned Reaper",
            "Vault Boy",
            "Marcus Fenix",
            "Leon Kennedy",
            "HK-47",
            "Sam & Max",
            "Pyramid Head",
            "Dr Fred Eddison",
            "Mr. X",
            "Dante",
            "Alucard",
            "Miner Willy",
            "Kane",
            "Ubbosathla",
            "Quicksilver",
            "Haazen",
            "Black noir",
            "King ezekiel",
            "Power man",
            "Robin vi",
            "Velocity",
            "Kenneth irons",
            "Margali szardos",
            "Big Daddy",
            "Red Hulk",
            "Aspheera",
            "Spiderwoman",
            "The Masked Marvel",
            "Elijah Snow",
            "Scarecrow",
            "Nightwing",
            "Ultron",
            "Iron Patriot",
            "Boything",
            "Raven",
            "Massdriver",
            "Doctor Light",
            "Norman Bates",
            "Christie Monteiro",
            "Black Knight",
            "Agent"
    );

    private List<String> unusedNames;
    private final Random rand;

    public Names(){
        rand = new Random();
        unusedNames = new ArrayList<>(allNames);
    }

    public String getRandomName() {
        if(unusedNames.isEmpty()) {
            unusedNames = new ArrayList<>(allNames);
        }

        int i = rand.nextInt(unusedNames.size());
        return unusedNames.get(i);
    }

}
