package org.wikiRelationsHeadMaster.core;

import org.wikiRelationsHeadMaster.core.AgentCommunicationThreads.CircularAgentCommsThread;
import org.wikiRelationsHeadMaster.core.AgentCommunicationThreads.RankingAgentCommsThread;
import org.wikiRelationsHeadMaster.core.Parsers.JsonParsingClass;
import org.wikiRelationsHeadMaster.core.linkHandlers.RankedLinksHandler;
import org.wikiRelationsHeadMaster.core.linkHandlers.RawLinksHandler;
import org.wikiRelationsHeadMaster.core.linksObjects.LinksObject;
import org.wikiRelationsHeadMaster.core.networking.HttpRequestClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Objects;

public class ApplicationManager implements Runnable{
    public ArrayList<QueryThread> queryThreadsList = new ArrayList<>();
    String circularLinksAgentUrl = "http://127.0.0.1:8090/links/";
    String rankingAgentUrl = "http://127.0.0.1:9000/ranking/";
    public RankedLinksHandler rankedLinksHandler = new RankedLinksHandler(rankingAgentUrl, queryThreadsList);
    public RawLinksHandler rawLinksHandler = new RawLinksHandler(circularLinksAgentUrl, queryThreadsList);
    CircularAgentCommsThread circularAgentCommsThread = new CircularAgentCommsThread(this);
    RankingAgentCommsThread rankingAgentCommsThread = new RankingAgentCommsThread(this);

    public void addQueryThread(QueryThread queryThread) throws IOException, InterruptedException {
        queryThreadsList.add(queryThread);
        queryThread.initializeLinksObject();
    }

    public void setQueryThreadsList(ArrayList<QueryThread> queryThreadsList) {
        this.queryThreadsList = queryThreadsList;
    }

    public Hashtable<Integer, Hashtable<String, Object>> getLinksFromAgent() throws IOException {
        String response = HttpRequestClass.sendGETRequest(this.circularLinksAgentUrl);
        return JsonParsingClass.parseLinks(response);
    }

    public Hashtable<Integer, Hashtable<String, Object>> getLinksFromRankingAgent() throws IOException {
        String response = HttpRequestClass.sendGETRequest(this.rankingAgentUrl);
        return JsonParsingClass.parseRankedLinks(response);
    }

    public void sendAllRawReadyLinks() throws IOException, InterruptedException {
        this.rawLinksHandler.sendAllRawReadyLinks(queryThreadsList);
    }

    public void sendReadyCircularLinks() throws IOException, InterruptedException {
        this.rankedLinksHandler.sendAllReadyCircularLinks(queryThreadsList);
    }

    public void addThread(String query, Integer id) throws IOException, InterruptedException {
        QueryThread queryThreadOne = new QueryThread(query, id);
        this.addQueryThread(queryThreadOne);
    }

    public ArrayList<HashMap<String, String >> getRankedLinksById(Integer id) {
        ArrayList<HashMap<String, String >> topTenRanked = new ArrayList<>();
        for (QueryThread queryThread: queryThreadsList) {
            if (Objects.equals(queryThread.getID(), id)) {
                topTenRanked = queryThread.getTopTenRanked();
            }
        }
        return topTenRanked;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ApplicationManager applicationManager = new ApplicationManager();
        QueryThread queryThreadOne = new QueryThread("Iran", 20);
        applicationManager.addQueryThread(queryThreadOne);
//        applicationManager.addQueryThread(secondQueryThread);
//        applicationManager.addQueryThread(thirdQueryThread);
        applicationManager.sendAllRawReadyLinks();
        Hashtable<Integer, Hashtable<String, Object>> circularLinks;
        while (true) {
            Thread.sleep(20000);
            circularLinks = applicationManager.getLinksFromAgent();
            if (circularLinks.size() != 0) {
                break;
//            System.out.println(circularLinks);
            }

        }

        applicationManager.setQueryThreadsList(applicationManager.rawLinksHandler.updateAllQueryThreads(circularLinks));

        applicationManager.sendReadyCircularLinks();

        Hashtable<Integer, Hashtable<String, Object>> topTenRanked;
        while (true) {
            Thread.sleep(15000);
            topTenRanked = applicationManager.getLinksFromRankingAgent();
            if (!topTenRanked.isEmpty()) {
                break;
            }
        }

        applicationManager.setQueryThreadsList(applicationManager.rankedLinksHandler.updateAllQueryThreads(topTenRanked));
        for (QueryThread queryThread: applicationManager.queryThreadsList) {
            System.out.println(queryThread.getLinksObject().getTopTenLinks());
        }
    }

    @Override
    public void run() {
        new Thread(circularAgentCommsThread).start();
        new Thread(rankingAgentCommsThread).start();
        System.out.println("[+] Started Application");
    }
}
