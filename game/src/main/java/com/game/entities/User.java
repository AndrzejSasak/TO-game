package com.game.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    @JsonProperty("login")
    private String login;
    @JsonProperty("highscore")
    private long highScore;
    @JsonIgnore
    private long currentScore;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getHighScore() {
        return highScore;
    }

    public void setHighScore(long highScore) {
        this.highScore = highScore;
    }

    public long getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(long currentScore) {
        this.currentScore = currentScore;
    }

    @Override
    public String toString() {
        return login + " => Highest score: " + highScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
}
