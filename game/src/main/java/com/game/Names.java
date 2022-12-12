package com.game;

import java.util.*;

public class Names {
    private final String[] names = {
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
    };
    private final Random rand;
    private List<Integer> used;
    public Names(){
        rand = new Random();
        used = new ArrayList<>();
    }

    public String random(){
        if (used.size() == names.length)
            reset();

        while (true){
            Integer i = rand.nextInt(names.length);
            if (used.stream().anyMatch(index -> (index.equals(i))))
                continue;
            used.add(i);
            return names[i];
        }
    }

    private void reset(){
        used = new ArrayList<>();
    }
}
