package org.wikiRelationsHeadMaster.core.Parsers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.wikiRelationsHeadMaster.core.networking.HttpRequestClass;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class JsonParsingClass {
    public static @NotNull Hashtable<Integer, Hashtable<String, Object>> parseLinks(String responseBody) {
            JSONArray linkObjects = new JSONArray(responseBody);
        Hashtable<Integer, Hashtable<String, Object>> parsedTable = new Hashtable<>();
        for (int i = 0; i < linkObjects.length(); i++) {
            Hashtable<String, Object> linkObjectTable = new Hashtable<>();
            JSONObject linkObject = linkObjects.getJSONObject(i);
            Integer id = linkObject.getInt("id");
            String originalLink = linkObject.getString("Original Link");
            JSONArray jsonLinksArray = linkObject.getJSONArray("Links");
            ArrayList<String> links = new ArrayList<>();
            for (int j = 0; j < jsonLinksArray.length(); j++) {
                links.add((String) jsonLinksArray.get(j));
            }


            linkObjectTable.put("Original Link", originalLink);
            linkObjectTable.put("Links", links);
            parsedTable.put(id, linkObjectTable);
            System.out.println("Links Length: " + links.size());
        }

        return parsedTable;
    }

    public static @NotNull Hashtable<Integer, Hashtable<String, Object>> parseRankedLinks(String responseBody) {
        JSONArray linkObjects = new JSONArray(responseBody);
        Hashtable<Integer, Hashtable<String, Object>> parsedTable = new Hashtable<>();
        for (int i = 0; i < linkObjects.length(); i++) {
            Hashtable<String, Object> linkObjectTable = new Hashtable<>();
            JSONObject linkObject = linkObjects.getJSONObject(i);
            Integer id = linkObject.getInt("id");
            String originalLink = linkObject.getString("Original Link");
            JSONArray jsonLinksArray = linkObject.getJSONArray("Links");
            ArrayList<HashMap<String, String>> links = new ArrayList<>();
            for (Object linksObject: jsonLinksArray) {
                HashMap<String, String> linksHashTable = new Gson().fromJson(linksObject.toString(), HashMap.class);
                links.add(linksHashTable);
            }


            linkObjectTable.put("Original Link", originalLink);
            linkObjectTable.put("Links", links);
            parsedTable.put(id, linkObjectTable);
            System.out.println("Links Length: " + links.size());
        }

        return parsedTable;
    }

    public static void main(String[] args) throws IOException {
        String response = HttpRequestClass.sendGETRequest("http://127.0.0.1:9000/ranking");
        JsonParsingClass.parseRankedLinks(response);
    }
}