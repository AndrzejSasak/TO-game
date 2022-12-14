package com.game.leaderboard;

import com.game.entities.User;
import jakarta.xml.bind.annotation.*;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@XmlRootElement(name="Leaderboard")
@XmlAccessorType(XmlAccessType.FIELD)
public class Leaderboard {

    @XmlElementWrapper(name="Users")
    @XmlElement(name="User")
    private Set<User> users;

    public Leaderboard() {
        this.users = new TreeSet<>(Comparator.comparing(User::getHighScore).reversed());
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
