package org.wikiRelationsHeadMaster.core.Parsers;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Objects;

public class HtmlParser {
    public static ArrayList<String> findAllWikiLinks(Document document) throws InterruptedException {
        ArrayList<String> wikiLinks = new ArrayList<>();
        Elements aElements = document.select("a");
        for (Element aElement : aElements) {
            String link = aElement.attr("href");
            String[] linkParts = link.split("/");
            try {
                if (Objects.equals(linkParts[3], "wiki")) {
                    if (!link.contains(":") && !link.contains("%")) {
                        if (!wikiLinks.contains("https://en.wikipedia.org" + link)) {
                            wikiLinks.add("https:" + link);
                            System.out.println(link);
                }}}
            } catch (Exception ignored) {
            }

        }

        System.out.println(wikiLinks.size());

        return wikiLinks;
}
}
