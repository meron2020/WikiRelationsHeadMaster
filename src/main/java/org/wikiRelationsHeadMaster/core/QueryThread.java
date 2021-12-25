package org.wikiRelationsHeadMaster.core;

import org.jsoup.nodes.Document;
import org.wikiRelationsHeadMaster.core.Parsers.HtmlParser;
import org.wikiRelationsHeadMaster.core.linksObjects.LinksObject;
import org.wikiRelationsHeadMaster.core.Downloaders.PageDownloader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class QueryThread implements Runnable {
    public Integer ID;
    public String query;
    public LinksObject linksObject = new LinksObject();
    public boolean readyToBeSent = false;
    public boolean linksSent = false;
    public boolean readyToSendCircularLinks = false;
    public boolean sentCircularLinks = false;
    public boolean linksReceived = false;

    QueryThread(String query, Integer id) {
        this.query = query;
        this.ID = id;
    }

    public boolean isLinksReceived() {
        return linksReceived;
    }

    public void setTopTenRankedLinks(ArrayList<HashMap<String,String>> topTenRankedLinks) {
        this.linksObject.setTopTenLinks(topTenRankedLinks);
    }

    public boolean isSentCircularLinks() {
        return sentCircularLinks;
    }

    public void setSentCircularLinks(boolean sentCircularLinks) {
        this.sentCircularLinks = sentCircularLinks;
    }

    public void setReadyToSendCircularLinks(boolean readyToSendCircularLinks) {
        this.readyToSendCircularLinks = readyToSendCircularLinks;
    }

    public boolean isReadyToSendCircularLinks() {
        return readyToSendCircularLinks;
    }

    public ArrayList<HashMap<String, String >> getTopTenRanked() {
        return this.linksObject.getTopTenLinks();
    }

    public Integer getID() {
        return ID;
    }

    public LinksObject getLinksObject() {
        return linksObject;
    }

    public void setLinksObject(LinksObject linksObject) {
        this.linksObject = linksObject;
    }

    public void setLinksObjectLinks(ArrayList<String> links) {
        this.linksObject.setLinks(links);
    }

    public void setOriginalLink(String link) {
        this.linksObject.setOriginalLink(link);
    }

    public void setLinksSent(boolean linksSent) {
        this.linksSent = linksSent;
    }

    public boolean isLinksSent() {
        return linksSent;
    }

    public void setLinksReceived(boolean linksReceived) {
        this.linksReceived = linksReceived;
    }

    public void setReadyToBeSent(boolean readyToBeSent) {
        this.readyToBeSent = readyToBeSent;
    }

    public boolean isReadyToBeSent() {
        return readyToBeSent;
    }

    public void initializeLinksObject() throws IOException, InterruptedException {
        String link = PageDownloader.getPageLinkFromQuery(this.query);
        Document queryDoc = PageDownloader.downloadPage(link);
        this.linksObject.setLinks(HtmlParser.findAllWikiLinks(queryDoc));
        String wikiLink = "https://en.wikipedia.org/wiki/" + query;
        this.linksObject.setOriginalLink(wikiLink);
        this.linksObject.setId(this.ID);
        this.readyToBeSent = true;
    }

    public Object getLinksObjectFromHash(Hashtable<Integer, Object> hashtable) {
        return hashtable.get(this.ID);
    }


    @Override
    public void run() {
        try {
            initializeLinksObject();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
