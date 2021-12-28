package org.wikiRelationsHeadMaster.core.HeadMasterObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;

public class LinksObject {
    @JsonProperty("Original Link")
    public String originalLink;
    @JsonProperty("Links")
    public ArrayList<String> links;
    @JsonProperty("id")
    public Integer id;
    public ArrayList<HashMap<String, String>> topTenLinks = new ArrayList<>();

    public void setTopTenLinks(ArrayList<HashMap<String, String>> topTenLinks) {
        this.topTenLinks = topTenLinks;
    }

    public ArrayList<HashMap<String, String>> getTopTenLinks() {
        return topTenLinks;
    }

    public String getOriginalLink() {
        return originalLink;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLinks(ArrayList<String> links) {
        this.links = links;
    }

    public void setOriginalLink(String originalLink) {
        this.originalLink = originalLink;
    }

    public Integer getId() {
        return id;
    }

    public ArrayList<String> getLinks() {
        return links;
    }
}
