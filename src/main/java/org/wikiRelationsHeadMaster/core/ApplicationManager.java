package org.wikiRelationsHeadMaster.core;

import org.wikiRelationsHeadMaster.core.linkHandlers.RankedLinksHandler;
import org.wikiRelationsHeadMaster.core.linkHandlers.RawLinksHandler;
import org.wikiRelationsHeadMaster.core.linksObjects.LinksObject;

import java.io.IOException;
import java.util.ArrayList;

public class ApplicationManager {
    public ArrayList<QueryThread> queryThreadsList = new ArrayList<>();
    public RankedLinksHandler rankedLinksHandler = new RankedLinksHandler();
    public RawLinksHandler rawLinksHandler = new RawLinksHandler(queryThreadsList);

    public void addQueryThread(QueryThread queryThread) {
        queryThreadsList.add(queryThread);
    }

    public void sendLinks(ArrayList<LinksObject> linksObjects) throws IOException, InterruptedException {
        rawLinksHandler.sendLinks(linksObjects);
    }

    public void sendAllReadyLinks() throws IOException, InterruptedException {
        ArrayList<LinksObject> linksObjects = new ArrayList<>();
        for (QueryThread queryThread: queryThreadsList) {
            linksObjects.add(queryThread.getLinksObject());
        }
        this.sendLinks(linksObjects);
    }


}
