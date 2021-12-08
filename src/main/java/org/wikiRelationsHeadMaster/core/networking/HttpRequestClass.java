package org.wikiRelationsHeadMaster.core.networking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestClass {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();


    public static String sendPOSTRequest(String link, Integer id, String originalLink, ArrayList<String> links)
            throws IOException, InterruptedException {
        Map<Object, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("originalLink", originalLink);
        data.put("Links", links);

        ObjectMapper mapper = new ObjectMapper();

        String yourBody = mapper.writeValueAsString(data);


        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(yourBody))
                .uri(URI.create(link))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public static String sendGETRequest(String link) {
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();

        try {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //Request setup
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);

            //Test if the response from the server is successfully
            int status = conn.getResponseCode();

            if (status >= 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseContent.toString();
    }
}

