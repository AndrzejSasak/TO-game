package com.game.leaderboard;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.File;

public class LeaderboardParser implements ILeadeboardParser {

    private final JAXBContext jaxbContext;
    private final String LEADERBOARD_PATH = "./game/src/main/resources/leaderboard.xml";

    public LeaderboardParser() throws JAXBException {
        this.jaxbContext =  JAXBContext.newInstance(Leaderboard.class);
    }

    @Override
    public Leaderboard readLeaderboard() throws JAXBException {
        return (Leaderboard) jaxbContext
                .createUnmarshaller()
                .unmarshal(new File(LEADERBOARD_PATH));
    }

    @Override
    public void saveLeaderboard(Leaderboard leaderboard) throws JAXBException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(leaderboard, new File(LEADERBOARD_PATH));
    }
}
