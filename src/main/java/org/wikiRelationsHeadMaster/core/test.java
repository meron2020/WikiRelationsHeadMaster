package org.wikiRelationsHeadMaster.core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.wikiRelationsHeadMaster.core.Parsers.HtmlParser;
import org.wikiRelationsHeadMaster.core.Parsers.JsonParsingClass;
import org.wikiRelationsHeadMaster.core.networking.HttpRequestClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class test extends Thread {

    public void run(int num) {

    }

    public void addString(String string, ArrayList<String> arrayList) {
        arrayList.add(string);
    }

    public static void main(String[] args) throws InterruptedException, IOException
    {
//        ArrayList<HashMap<String, String >> topTenRanked;
//        ApplicationManager applicationManager = new ApplicationManager();
////        applicationManager.startApplication();
//        applicationManager.addThread("Israel", 20);
//        while (true) {
//            ArrayList<HashMap<String, String >> ranked = applicationManager.getRankedLinksById(20);
//            if (!ranked.isEmpty()) {
//                topTenRanked = ranked;
//                break;
//            }
//            Thread.sleep(15000);
//        }
//        System.out.println(topTenRanked);

    }


    private void start(int num) throws InterruptedException {
        while (true) {
            System.out.println(num);
            Thread.sleep(4000);
    }}


}
