package com.game;


import com.game.entities.Entity;

public class Messages {
    public static void attackMessage(Entity attacker, Entity attacked){
        String message = attacker.getNameInfo() + " attacks: " + attacked.getNameInfo() + "!";
        System.out.println(message);
    }
    public static void areaAttackMessage(Entity attacker){
        String message = attacker.getNameInfo() + " uses area attack!";
        System.out.println(message);
    }
    public static void hitMessage(Entity attacked, int damages) {
        String message = attacked.getName() + " takes: " + damages + " damage!";
        System.out.println(message);
    }
    public static void passMessage(Entity entity) {
        String message = entity.getNameInfo() + " skips round!";
        System.out.println(message);
    }
    public static void deathMessage(Entity dead){
        String message = "Info: " + dead.getName() + " is dead!";
        System.out.println(message);
    }
}
