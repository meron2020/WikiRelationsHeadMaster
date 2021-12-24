package org.wikiRelationsHeadMaster.core.linkHandlers;

import org.wikiRelationsHeadMaster.core.Parsers.JsonParsingClass;
import org.wikiRelationsHeadMaster.core.QueryThread;
import org.wikiRelationsHeadMaster.core.linksObjects.LinksObject;
import org.wikiRelationsHeadMaster.core.networking.HttpRequestClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class RawLinksHandler {
    String url;
    ArrayList<QueryThread> queryThreadArrayList;

    public RawLinksHandler(String url, ArrayList<QueryThread> queryThreadArrayList) {
        this.url = url;
        this.queryThreadArrayList = queryThreadArrayList;
    }

    public ArrayList<LinksObject> getListObjectFromQueryThreads(ArrayList<QueryThread> queryThreadList) {
        ArrayList<LinksObject> linksObjectArrayList = new ArrayList<>();
        for (QueryThread queryThread: queryThreadList) {
            if (queryThread.isReadyToBeSent()){
            linksObjectArrayList.add(queryThread.getLinksObject());
        }}

        return linksObjectArrayList;
    }

    public void sendAllRawReadyLinks(ArrayList<QueryThread> queryThreadsList) throws IOException, InterruptedException {
        ArrayList<LinksObject> linksObjects = new ArrayList<>();
        for (QueryThread queryThread : queryThreadsList) {
            if (queryThread.isReadyToBeSent() && !queryThread.isLinksSent())
                linksObjects.add(queryThread.getLinksObject());
                queryThread.setLinksSent(true);
        }
        sendLinks(linksObjects);
        if (!linksObjects.isEmpty()) {
            System.out.println("Sent raw links");
        }
    }


    public Hashtable<Integer, Hashtable<String, Object>> getRankedLinksAndParse() throws IOException {
        String getResponse = HttpRequestClass.sendGETRequest(this.url);
        return JsonParsingClass.parseLinks(getResponse);
    }

    public ArrayList<QueryThread> updateAllQueryThreads(Hashtable<Integer, Hashtable<String, Object>> linksHash) {
        for (QueryThread queryThread: this.queryThreadArrayList) {
            if (linksHash.get(queryThread.getID()) != null) {
                Hashtable<String, Object> linkhash = linksHash.get(queryThread.getID());
                queryThread.setOriginalLink((String) linkhash.get("Original Link"));
                queryThread.setLinksObjectLinks((ArrayList<String>) linkhash.get("Links"));
                queryThread.setReadyToSendCircularLinks(true);
            }
        }
        return this.queryThreadArrayList;
    }

    public void sendLinks(ArrayList<LinksObject> linksObjects) throws IOException, InterruptedException {
        ArrayList<HashMap<String, Object>> hashMapArrayList =  HttpRequestClass.turnLinksArrayToHashmap(linksObjects);
        for (HashMap<String, Object> hashMap: hashMapArrayList) {
            HttpRequestClass.sendPOSTRequest(hashMap, this.url);
    }}
}
