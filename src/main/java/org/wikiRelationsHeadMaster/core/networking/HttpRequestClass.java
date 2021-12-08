package org.wikiRelationsHeadMaster.core.networking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.wikiRelationsHeadMaster.core.linksObjects.LinksObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;

public class HttpRequestClass {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();


    public static ArrayList<HashMap<String, Object>> turnLinksArrayToHashmap(ArrayList<LinksObject> linksObjectArrayList) {
        ArrayList<HashMap<String, Object>> hashMapArrayList = new ArrayList<>();
        for (LinksObject linksObject: linksObjectArrayList) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id", linksObject.getId());
            hashMap.put("links", linksObject.getLinks());
            hashMap.put("original link", linksObject.getOriginalLink());
            hashMapArrayList.add(hashMap);
        }

        return hashMapArrayList;

    }


    public static void sendPOSTRequest(ArrayList<HashMap<String, Object>> data, String link)
            throws IOException, InterruptedException {


        ObjectMapper mapper = new ObjectMapper();

        String yourBody = mapper.writeValueAsString(data);


        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(yourBody))
                .uri(URI.create(link))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

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

