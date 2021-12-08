package org.wikiRelationsHeadMaster.core.Parsers;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;

import java.util.ArrayList;

public class HtmlParser {
    public static ArrayList<String> findAllWikiLinks(Document document) {
        ArrayList<String> wikiLinks = new ArrayList<>();
        Elements aElements = document.select("a");
        for (Element element: aElements) {
            String link = element.attr("href");
            if (link.contains("https://en.wikipedia.org/wiki/")) {
                wikiLinks.add(link);
            }
        }
        return wikiLinks;
    }
}
