package com.game.entities;

import com.game.controllers.Player;
import jakarta.xml.bind.annotation.*;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@XmlRootElement(name="Leaderboard")
@XmlAccessorType(XmlAccessType.FIELD)
public class Leaderboard {

    @XmlElementWrapper(name="Players")
    @XmlElement(name="Player")
    private Set<Player> players;

    public Leaderboard() {
        this.players = new TreeSet<>(Comparator.comparing(Player::getHighScore).reversed());
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }


    public void addPlayer(Player player) {
        this.players.add(player);
    }

    @Override
    public String toString() {
        StringBuilder leaderboardBuilder = new StringBuilder("Leaderboard: \n");

        for(Player player : players) {
            leaderboardBuilder.append(player.toString()).append("\n");
        }

        return leaderboardBuilder.toString();
    }
}
