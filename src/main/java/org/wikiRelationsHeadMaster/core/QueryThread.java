package org.wikiRelationsHeadMaster.core;

import org.jsoup.nodes.Document;
import org.wikiRelationsHeadMaster.core.Parsers.HtmlParser;
import org.wikiRelationsHeadMaster.core.linksObjects.LinksObject;
import org.wikiRelationsHeadMaster.core.Downloaders.PageDownloader;

import java.io.IOException;
import java.util.Hashtable;

public class QueryThread extends Thread {
    public Integer id;
    public String query;
    public LinksObject linksObject = new LinksObject();
    public boolean readyToBeSent = false;
    public boolean linksSent = false;
    public boolean linksReceived = false;

    QueryThread(String query, Integer id) {
        this.query = query;
        this.id = id;
    }

    public LinksObject getLinksObject() {
        return linksObject;
    }

    public void setLinksObject(LinksObject linksObject) {
        this.linksObject = linksObject;
    }

    public void setLinksSent(boolean linksSent) {
        this.linksSent = linksSent;
    }

    public void setLinksReceived(boolean linksReceived) {
        this.linksReceived = linksReceived;
    }

    public void setReadyToBeSent(boolean readyToBeSent) {
        this.readyToBeSent = readyToBeSent;
    }

    public void initializeLinksObject(String query) throws IOException {
        String link = PageDownloader.getPageLinkFromQuery(query);
        Document queryDoc = PageDownloader.downloadPage(link);
        this.linksObject.setLinks(HtmlParser.findAllWikiLinks(queryDoc));
        this.linksObject.setOriginalLink(link);
        this.linksObject.setId(this.id);
        this.readyToBeSent = true;
    }

    public Object getLinksObjectFromHash(Hashtable<Integer, Object> hashtable) {
        return hashtable.get(this.id);
    }

    public void start() {

    }
}
