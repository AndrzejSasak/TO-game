import com.game.controllers.NPCEntityController;
import com.game.controllers.PlayerEntityController;
import com.game.entities.Entity;
import com.game.entities.User;
import com.game.leaderboard.Leaderboard;
import com.game.leaderboard.LeaderboardParserProxy;
import jakarta.xml.bind.JAXBException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(MockitoJUnitRunner.class)
class LeaderboardParserProxyTest {

    private LeaderboardParserProxy leaderboardParserProxy;
    private Entity entity;

    @Test
    void readLeaderboard_givenRealPlayerEntity_shouldReturnLeaderboardObject() {
        //given
        Entity entity = new Entity("dummyName", new PlayerEntityController(new User())) {
            @Override
            protected void init(int level) {

            }

            @Override
            public List<Entity> getPreferredTargets(List<Entity> allEnemies) {
                return null;
            }
        };

        try {
            leaderboardParserProxy = new LeaderboardParserProxy(entity);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        //when
        Leaderboard leaderboard = null;
        try {
            leaderboard = leaderboardParserProxy.readLeaderboard();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        //then
        assertEquals(leaderboard.getClass(), Leaderboard.class);
    }

    @Test
    void readLeaderboard_givenNPCEntity_shouldReturnNull() {
        //given
        Entity entity = new Entity("dummyName", new NPCEntityController()) {
            @Override
            protected void init(int level) {

            }

            @Override
            public List<Entity> getPreferredTargets(List<Entity> allEnemies) {
                return null;
            }
        };

        try {
            leaderboardParserProxy = new LeaderboardParserProxy(entity);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        //when
        Leaderboard leaderboard = null;
        try {
            leaderboard = leaderboardParserProxy.readLeaderboard();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        //then
        assertNull(leaderboard);
    }


}
