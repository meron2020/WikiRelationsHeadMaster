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
            hashMap.put("Links", linksObject.getLinks());
            hashMap.put("Original Link", linksObject.getOriginalLink());
            hashMapArrayList.add(hashMap);
        }

        return hashMapArrayList;

    }


    public static void sendPOSTRequest(HashMap<String, Object> data, String link)
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
    

    public static String sendGETRequest(String link) throws IOException {
        String responseToReturn = null;
        URL obj = new URL(link);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            responseToReturn = response.toString();
        } else {
            System.out.println("GET request not worked");
        }

        return responseToReturn;
    }
}

