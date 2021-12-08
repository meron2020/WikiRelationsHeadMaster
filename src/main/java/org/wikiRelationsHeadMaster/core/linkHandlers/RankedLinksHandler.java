package org.wikiRelationsHeadMaster.core.linkHandlers;

import org.wikiRelationsHeadMaster.core.networking.HttpRequestClass;
import org.wikiRelationsHeadMaster.core.Parsers.JsonParsingClass;

import java.util.Hashtable;

public class RankedLinksHandler {
    public String link;
    public Hashtable<Integer, Object> linksHashTable = new Hashtable<>();

    public Hashtable<Integer, Object> getRankedLinksAndParse() {
        String getResponse = HttpRequestClass.sendGETRequest(this.link);
        return JsonParsingClass.parseCircularLinks(getResponse);
    }


}
