package com.game.leaderboard;

import com.game.controllers.PlayerEntityController;
import jakarta.xml.bind.JAXBException;

public class LeaderboardParserProxy implements ILeadeboardParser {

    private LeaderboardParser leaderboardParser;
    private final PlayerEntityController playerEntityController;

    public LeaderboardParserProxy(PlayerEntityController playerEntityController) throws JAXBException {
        if(isPlayerLoggedIn()) {
            leaderboardParser=  new LeaderboardParser();
        }
        this.playerEntityController = playerEntityController;
    }

    private boolean isPlayerLoggedIn() {
        //TODO: check if user is playing with or without login
        return true;
    }

    @Override
    public Leaderboard readLeaderboard() throws JAXBException {
        if(isPlayerLoggedIn()) {
            return leaderboardParser.readLeaderboard();
        }

        return null;
    }

    @Override
    public void saveLeaderboard(Leaderboard leaderboard) throws JAXBException {
        if(isPlayerLoggedIn()) {
            leaderboardParser.saveLeaderboard(leaderboard);
        }

    }
}
