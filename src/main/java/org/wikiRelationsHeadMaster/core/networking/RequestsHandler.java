package org.wikiRelationsHeadMaster.core.networking;

import org.wikiRelationsHeadMaster.core.LinksObject;
import org.wikiRelationsHeadMaster.core.QueryThread;

import java.util.ArrayList;

public class RequestsHandler {
    public ArrayList<LinksObject> waitingToSend = new ArrayList<>();
    public ArrayList<LinksObject> received = new ArrayList<>();
    public ArrayList<QueryThread> queryThreads = new ArrayList<>();

    public void addLinksToSend(LinksObject linksObject) {
        waitingToSend.add(linksObject);
    }

    public void receive() {

    }

}
