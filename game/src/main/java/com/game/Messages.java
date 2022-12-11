package com.game;


import com.game.entities.Entity;

public class Messages {
    public static void attackMessage(Entity attacker, Entity attacked){
        String message = "Player: " + attacker.getName() + " attacks: " + attacked.getName()+ "!";
        System.out.println(message);
    }
    public static void areaAttackMessage(Entity attacker){
        String message = "Player: " + attacker.getName() + " uses area attack!";
        System.out.println(message);
    }
    public static void hitMessage(Entity attacked, int damages) {
        String message = "Player: " + attacked.getName() + " takes: " + damages + " damage!";
        System.out.println(message);
    }
    public static void passMessage(Entity entity) {
        String message = "Player: " + entity.getName() + " skips round!";
        System.out.println(message);
    }
    public static void deathMessage(Entity dead){
        String message = "Player: " + dead.getName() + " is dead!";
        System.out.println(message);
    }
}
