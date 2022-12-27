package com.game.leaderboard;

import com.game.entities.User;

import java.util.*;

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

        List<User> userList = new ArrayList<>(users);
        userList.sort(Comparator.comparing(User::getHighScore).reversed());

        for(User user : userList) {
            leaderboardBuilder.append(user.toString()).append("\n");
        }

        return leaderboardBuilder.toString();
    }
}
