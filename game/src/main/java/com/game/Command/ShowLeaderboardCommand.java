package com.game.Command;

import com.game.controllers.PlayerEntityController;
import com.game.entities.Entity;
import com.game.leaderboard.Leaderboard;
import com.game.leaderboard.LeaderboardParserProxy;
import jakarta.xml.bind.JAXBException;

public class ShowLeaderboardCommand implements ICommand{
    private Entity player;
    public ShowLeaderboardCommand(Entity player) {
        this.player = player;
    }

    @Override
    public void execute() {
        try{
            Leaderboard leaderboard = new LeaderboardParserProxy(player).readLeaderboard();
            System.out.println(leaderboard.toString());
        }
        catch (JAXBException ex) {
            System.out.println("Cannot read leaderboard: " + ex.getMessage());
        }
    }
}
