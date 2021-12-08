package org.wikiRelationsHeadMaster.core.linksObjects;

import java.util.ArrayList;

public class LinkedObject {
    public String originalLink;
    public ArrayList<String> links;

    public void setOriginalLink(String originalLink) {
        this.originalLink = originalLink;
    }

    public void setLinks(ArrayList<String> links) {
        this.links = links;
    }

    public ArrayList<String> getLinks() {
        return links;
    }

    public String getOriginalLink() {
        return originalLink;
    }
}
