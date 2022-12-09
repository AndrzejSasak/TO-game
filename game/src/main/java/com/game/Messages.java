package com.game;

import javax.swing.text.html.parser.Entity;

public class Messages {
    public static String attackMessage(Entity attacker, Entity attacked, int attackPoints){
        return "Player: " + attacker.getName() + " attacks: " +attacked.getName()+ "\n";
    }
    public static String hitMessage(Entity attacked, int damages){
        return "Player: " + attacked.getName() + " takes: " + damages + " damage\n";
    }
}
