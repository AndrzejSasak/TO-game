package com.game.leaderboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.entities.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class LeaderboardParser implements ILeadeboardParser {

    private final String LEADERBOARD_URL;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    public LeaderboardParser() {
        LEADERBOARD_URL = "https://jsonblob.com/api/1054741520221224960";
        this.objectMapper = new ObjectMapper();
        httpClient = HttpClientBuilder.create().build();
    }

    public LeaderboardParser(String LEADERBOARD_URL) {
        this.LEADERBOARD_URL = LEADERBOARD_URL;
        this.objectMapper = new ObjectMapper();
        httpClient = HttpClientBuilder.create().build();
    }

    @Override
    public Leaderboard readLeaderboard()  {
        HttpGet httpGet = getHttpGet();
        HttpResponse response = sendHttpGetRequest(httpGet);
        String json = getJsonStringFromResponse(response);
        Set<User> users = getUserSet(json);
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setUsers(users);

        return leaderboard;
    }

    @Override
    public void saveLeaderboard(Leaderboard leaderboard)  {
        String leaderboardJson = getLeaderboardJson(leaderboard);
        HttpPut httpPut = getHttpPut(leaderboardJson);
        HttpResponse httpResponse = sendHttpPutRequest(httpPut);
    }

    private HttpResponse sendHttpPutRequest(HttpPut httpPut) {
        HttpResponse httpResponse = null;
        try {
            httpResponse = this.httpClient.execute(httpPut);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    private HttpGet getHttpGet() {
        HttpGet httpGet = new HttpGet(LEADERBOARD_URL);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-type", "application/json");
        return httpGet;
    }

    private HttpPut getHttpPut(String leaderboardJson) {
        HttpPut httpPut = new HttpPut(LEADERBOARD_URL);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");
        StringEntity stringEntity = getStringEntity(leaderboardJson);
        httpPut.setEntity(stringEntity);
        return httpPut;
    }

    private StringEntity getStringEntity(String leaderboardJson) {
        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(leaderboardJson);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringEntity;
    }

    private String getLeaderboardJson(Leaderboard leaderboard) {
        String leaderboardJson = null;
        try {
            leaderboardJson = this.objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(leaderboard.getUsers());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return leaderboardJson;
    }

    private Set<User> getUserSet(String json) {
        Set<User> users = new TreeSet<>(Comparator.comparing(User::getLogin));
        try {
            users = new ObjectMapper().readValue(json, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return users;
    }

    private String getJsonStringFromResponse(HttpResponse response) {
        String json = null;
        try {
            json = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    private HttpResponse sendHttpGetRequest(HttpGet httpGet) {
        HttpResponse response = null;
        try {
            response = this.httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
