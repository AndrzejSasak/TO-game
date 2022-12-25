package com.game.leaderboard;

import com.game.entities.User;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Leaderboard {

    private Set<User> users;

    public Leaderboard() {
        this.users = new HashSet<>();
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> playerEntityControllers) {
        this.users = playerEntityControllers;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    @Override
    public String toString() {
        StringBuilder leaderboardBuilder = new StringBuilder("Leaderboard: \n");

        for(User user : users) {
            leaderboardBuilder.append(user.toString()).append("\n");
        }

        return leaderboardBuilder.toString();
    }
}
