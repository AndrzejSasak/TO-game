import com.game.entities.User;
import com.game.leaderboard.Leaderboard;
import com.game.leaderboard.LeaderboardParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static  org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class LeaderboardParserTest {

    private final String TEST_LEADERBOARD_URL = "https://jsonblob.com/api/1061997262347190272";
    private final HttpClient httpClient = HttpClientBuilder.create().build();
    private final LeaderboardParser leaderboardParser = new LeaderboardParser(TEST_LEADERBOARD_URL);

    @BeforeEach
    public void setup() {

    }

    @Test
    void readLeaderboard_whenLeaderboardEmpty_ShouldReturnEmptyJSONArray() {
        //given
        prepareEmptyLeadeboard();
        //when
        Leaderboard leaderboard = leaderboardParser.readLeaderboard();
        //then
        assertEquals("[]", leaderboard.getUsers().toString());
    }

    @Test
    void readLeaderboard_whenLeaderboardHasOneUser_ShouldReturnCorrectUsers() {
        //given
        prepareLeadeboardWithOneUser();
        //when
        Leaderboard leaderboard = leaderboardParser.readLeaderboard();
        //then
        User expected = new User();
        expected.setLogin("testUser");
        expected.setHighScore(123456789);
        System.out.println(leaderboard.getUsers());
        assertTrue(leaderboard.getUsers().contains(expected));
    }

    @Test
    void saveLeadeboard_whenUserAddedToEmptyLeaderboard_LeaderboardShouldContainOneUser() {
        //given
        prepareEmptyLeadeboard();
        Leaderboard leaderboard = new Leaderboard();
        User user = new User();
        user.setLogin("testUser");
        user.setHighScore(123456789);
        leaderboard.addUser(user);
        //when
        leaderboardParser.saveLeaderboard(leaderboard);
        //then
        String actual = readContentsFromUrl()
                .replaceAll("\n", "")
                .replaceAll("\r", "");
        String expected = """
                [ {
                  "login" : "testUser",
                  "highscore" : 123456789
                } ]"""
                .replaceAll("\n", "")
                .replaceAll("\r", "");
        assertEquals(expected, actual);
    }

    @Test
    void saveLeadboard_whenSavingEmptyLeadeboard_shouldContainEmptyJSONArray() {
        //given
        prepareLeadeboardWithOneUser();
        Leaderboard leaderboard = new Leaderboard();
        //when
        leaderboardParser.saveLeaderboard(leaderboard);
        //then
        String actual = readContentsFromUrl();
        assertEquals("[ ]", actual);
    }

    private void prepareEmptyLeadeboard() {
        HttpPut httpPut = new HttpPut(TEST_LEADERBOARD_URL);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-Type", "application/json");
        StringEntity stringEntity = null;

        try {
            stringEntity = new StringEntity("[]");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        httpPut.setEntity(stringEntity);

        try {
            HttpResponse httpResponse = this.httpClient.execute(httpPut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prepareLeadeboardWithOneUser() {
        HttpPut httpPut = new HttpPut(TEST_LEADERBOARD_URL);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-Type", "application/json");
        StringEntity stringEntity = null;

        try {
            String contentWithOneUser = """
                    [ {
                      "login" : "testUser",
                      "highscore" : 123456789
                    } ]
                    """
                    .replaceAll("\n", "")
                    .replaceAll("\r", "");
            stringEntity = new StringEntity(contentWithOneUser);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        httpPut.setEntity(stringEntity);

        try {
            HttpResponse httpResponse = this.httpClient.execute(httpPut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readContentsFromUrl() {
        HttpGet httpGet = new HttpGet(TEST_LEADERBOARD_URL);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-type", "application/json");
        HttpResponse response = null;
        try {
            response = this.httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = null;
        try {
            json = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

}
