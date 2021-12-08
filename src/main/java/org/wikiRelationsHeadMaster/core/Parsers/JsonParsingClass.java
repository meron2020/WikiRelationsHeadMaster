package org.wikiRelationsHeadMaster.core.Parsers;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;

public class JsonParsingClass {
    public static @NotNull Hashtable<Integer, Object> parseCircularLinks(String responseBody) {
            JSONArray linkObjects = new JSONArray(responseBody);
        Hashtable<Integer, Object> parsedTable = new Hashtable<>();
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
        }

        return parsedTable;
    }

}