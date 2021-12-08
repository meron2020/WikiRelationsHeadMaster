package org.wikiRelationsHeadMaster.core.linkHandlers;

import org.wikiRelationsHeadMaster.core.QueryThread;
import org.wikiRelationsHeadMaster.core.linksObjects.LinksObject;
import org.wikiRelationsHeadMaster.core.networking.HttpRequestClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RawLinksHandler {
    public ArrayList<QueryThread> queryThreadList;
    String url;

    public RawLinksHandler(ArrayList<QueryThread> queryThreadList) {
        this.queryThreadList = queryThreadList;

    }

//    public ArrayList<LinksObject> getListObjectFromQueryThreads() {
//        for (QueryThread queryThread: queryThreadList) {
//
//        }
//    }

    public void addLinksToSend(QueryThread queryThread) {
        queryThreadList.add(queryThread);

    }

    public void sendLinks(ArrayList<LinksObject> linksObjects) throws IOException, InterruptedException {
        ArrayList<HashMap<String, Object>> hashMapArrayList =  HttpRequestClass.turnLinksArrayToHashmap(linksObjects);
        HttpRequestClass.sendPOSTRequest(hashMapArrayList, this.url);
    }
}
