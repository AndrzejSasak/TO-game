package com.game.leaderboard;

import jakarta.xml.bind.JAXBException;

public interface ILeadeboardParser {

    Leaderboard readLeaderboard() throws JAXBException;
    void saveLeaderboard(Leaderboard leaderboard) throws JAXBException;

}
