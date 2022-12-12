package com.game.controllers;

import com.game.entities.Entity;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;

import java.util.List;
import java.util.Scanner;

@XmlAccessorType(XmlAccessType.FIELD)
public class Player implements EntityController{

    @XmlElement(name="login")
    private String login;

    @XmlElement(name="highScore")
    private long highScore;

    @XmlTransient
    private long currentScore;


    @Override
    public Entity getNextTarget(Entity entity, List<Entity> allFriends, List<Entity> allEnemies) {
        //TODO command
        System.out.println("--------------------------");
        System.out.println("Your turn!\nYour team:");
        for (Entity target : allFriends) {
            System.out.println(target.getNameInfo());
        }
        System.out.println("Choose option:");
        int i;
        for (i = 0; i < allEnemies.size(); i++){
            Entity target = allEnemies.get(i);
            System.out.println(i + ". Attack: " + target.getNameInfo());
        }
        System.out.println(i + ". Skip turn.");
        System.out.println("--------------------------");
        Scanner myInput = new Scanner( System.in );
        int a = myInput.nextInt();
        if(a >= allEnemies.size() || a < 0)
            return null;
        return allEnemies.get(a);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(long currentScore) {
        this.currentScore = currentScore;
    }

    public long getHighScore() {
        return highScore;
    }

    public void setHighScore(long highScore) {
        this.highScore = highScore;
    }

    @Override
    public String toString() {
        return login + " => Highest score: " + highScore;
    }
}
