package services;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import jdk.nashorn.internal.parser.JSONParser;
import models.XYobject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URL;
import java.util.ArrayList;

public class DataService {

    private static final String USER_AGENT = "Mozilla/5.0";

    public JSONArray getData(String endpoint) throws Exception {
        HttpURLConnection con = getConnection(endpoint);
        if (con.getResponseCode() != HttpURLConnection.HTTP_OK)
            throw new Exception("API Problem");
        StringBuilder response = getResponse(con);
        return new JSONArray(response.toString());
    }



    public JSONObject getObject(String endpoint) throws Exception {
        HttpURLConnection con = getConnection(endpoint);
        if (con.getResponseCode() != HttpURLConnection.HTTP_OK)
            throw new Exception("API Problem");
        StringBuilder response = getResponse(con);
        return new JSONObject(response.toString());
    }

    private HttpURLConnection getConnection(String endpoint) throws IOException {
        URL obj = new URL("http://localhost:8080/" + endpoint);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        return con;
    }

    private StringBuilder getResponse(HttpURLConnection con) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = bufferedReader.readLine()) != null)
            response.append(inputLine);
        bufferedReader.close();

        return response;
    }

}
