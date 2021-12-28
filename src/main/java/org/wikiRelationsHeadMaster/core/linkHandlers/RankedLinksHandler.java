package org.wikiRelationsHeadMaster.core.linkHandlers;

import org.wikiRelationsHeadMaster.core.QueryThread;
import org.wikiRelationsHeadMaster.core.HeadMasterObjects.LinksObject;
import org.wikiRelationsHeadMaster.core.networking.HttpRequestClass;
import org.wikiRelationsHeadMaster.core.Parsers.JsonParsingClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class RankedLinksHandler {
    public String link;
    public Hashtable<Integer, Object> linksHashTable = new Hashtable<>();
    ArrayList<QueryThread> queryThreadArrayList;

    public Hashtable<Integer, Hashtable<String, Object>> getRankedLinksAndParse() throws IOException {
        String getResponse = HttpRequestClass.sendGETRequest(this.link);
        return JsonParsingClass.parseLinks(getResponse);
    }

    public RankedLinksHandler(String link, ArrayList<QueryThread> queryThreadArrayList) {
        this.link = link;
        this.queryThreadArrayList = queryThreadArrayList;
    }


    public void sendAllReadyCircularLinks(ArrayList<QueryThread> queryThreadsList) throws IOException, InterruptedException {
        ArrayList<LinksObject> linksObjects = new ArrayList<>();
        for (QueryThread queryThread: queryThreadsList) {
            if (queryThread.isReadyToSendCircularLinks() && !queryThread.isSentCircularLinks()) {
                linksObjects.add(queryThread.getLinksObject());
                queryThread.setSentCircularLinks(true);
            }

        }
        sendLinks(linksObjects);
    }


    public ArrayList<QueryThread> updateAllQueryThreads(Hashtable<Integer, Hashtable<String, Object>> linksHash) {
        for (QueryThread queryThread: this.queryThreadArrayList) {
            if (linksHash.get(queryThread.getID()) != null) {
                Hashtable<String, Object> linkHash = linksHash.get(queryThread.getID());
                queryThread.setOriginalLink((String) linkHash.get("Original Link"));
                queryThread.setTopTenRankedLinks((ArrayList<HashMap<String, String>>) linkHash.get("Links"));
                queryThread.setLinksReceived(true);
            }
        }
        return this.queryThreadArrayList;
    }

//    public static void main(String[] args) throws IOException {
//        RankedLinksHandler rankedLinksHandler = new RankedLinksHandler();
//        System.out.println(rankedLinksHandler.getRankedLinksAndParse());
//    }


    public void sendLinks(ArrayList<LinksObject> linksObjects) throws IOException, InterruptedException {
        ArrayList<HashMap<String, Object>> hashMapArrayList =  HttpRequestClass.turnLinksArrayToHashmap(linksObjects);
        for (HashMap<String, Object> hashMap: hashMapArrayList) {
            HttpRequestClass.sendPOSTRequest(hashMap, this.link);
        }}


}
