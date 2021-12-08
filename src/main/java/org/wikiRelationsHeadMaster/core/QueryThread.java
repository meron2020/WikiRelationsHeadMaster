package org.wikiRelationsHeadMaster.core;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class QueryThread {
    public Integer id;
    public String query;

    QueryThread(String query, Integer id) {
        this.query = query;
        this.id = id;
    }

    public Document downloadQueryPage(String query) throws IOException {
        String link = PageDownloader.getPageLinkFromQuery(query);
        return PageDownloader.downloadPage(link);
    }

    public Object getLinksObjectFromHash(Hashtable<Integer, Object> hashtable) {
        return hashtable.get(this.id);
    }

}
