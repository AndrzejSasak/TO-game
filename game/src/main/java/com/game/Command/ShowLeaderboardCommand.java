package com.game.Command;

import com.game.controllers.PlayerEntityController;
import com.game.leaderboard.Leaderboard;
import com.game.leaderboard.LeaderboardParserProxy;
import jakarta.xml.bind.JAXBException;

public class ShowLeaderboardCommand implements ICommand{
    private PlayerEntityController playerEntityController;
    public ShowLeaderboardCommand(PlayerEntityController playerEntityController) {
        this.playerEntityController = playerEntityController;
    }

    @Override
    public void execute() {
        try{
            Leaderboard leaderboard = new LeaderboardParserProxy(playerEntityController).readLeaderboard();
            System.out.println(leaderboard.toString());
        }
        catch (JAXBException ex) {
            System.out.println("Cannot read leaderboard: " + ex.getMessage());
        }
    }
}
