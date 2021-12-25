package org.wikiRelationsHeadMaster.core.Downloaders;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class PageDownloader {
    public static Document downloadPage(String link) throws IOException {
        return Jsoup.connect(link).get();
    }

    public static String getPageLinkFromQuery(String query) {
        return String.format("http://en.wikipedia.org/w/index.php?action=render&title=%s", query);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(downloadPage(PageDownloader.getPageLinkFromQuery("Israel")));

    }

}
