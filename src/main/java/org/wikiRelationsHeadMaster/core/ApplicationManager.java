package org.wikiRelationsHeadMaster.core;

import org.wikiRelationsHeadMaster.core.AgentCommunicationThreads.CircularAgentCommsThread;
import org.wikiRelationsHeadMaster.core.AgentCommunicationThreads.RankingAgentCommsThread;
import org.wikiRelationsHeadMaster.core.AgentHandlers.AgentsHandler;
import org.wikiRelationsHeadMaster.core.HeadMasterObjects.AgentObject;
import org.wikiRelationsHeadMaster.core.Parsers.JsonParsingClass;
import org.wikiRelationsHeadMaster.core.linkHandlers.RankedLinksHandler;
import org.wikiRelationsHeadMaster.core.linkHandlers.RawLinksHandler;
import org.wikiRelationsHeadMaster.core.networking.HttpRequestClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Objects;

public class ApplicationManager implements Runnable{
    public AgentsHandler agentsHandler;
    public ArrayList<QueryThread> queryThreadsList = new ArrayList<>();
    String circularLinksAgentUrl = "http://127.0.0.1:8090/links/";
    String rankingAgentUrl = "http://127.0.0.1:9000/ranking/";
    public RankedLinksHandler rankedLinksHandler = new RankedLinksHandler(rankingAgentUrl, queryThreadsList);
    public RawLinksHandler rawLinksHandler = new RawLinksHandler(circularLinksAgentUrl, queryThreadsList);
    CircularAgentCommsThread circularAgentCommsThread = new CircularAgentCommsThread(this);
    RankingAgentCommsThread rankingAgentCommsThread = new RankingAgentCommsThread(this);

    public ApplicationManager(AgentsHandler agentsHandler) {
        this.agentsHandler = agentsHandler;
    }

    public void addQueryThread(QueryThread queryThread) throws IOException, InterruptedException {
        queryThreadsList.add(queryThread);
        queryThread.initializeLinksObject();
    }

    public void setQueryThreadsList(ArrayList<QueryThread> queryThreadsList) {
        this.queryThreadsList = queryThreadsList;
    }

    public Hashtable<Integer, Hashtable<String, Object>> getLinksFromAgent() throws IOException {
        Hashtable<Integer, Hashtable<String, Object>> linksHash = new Hashtable<>();
        for (AgentObject agentObject: agentsHandler.getCircularAgentsList()) {
        String response = HttpRequestClass.sendGETRequest(agentObject.getCommunicationUrl());
        Hashtable<Integer, Hashtable<String, Object>> parsedLinks = JsonParsingClass.parseLinks(response);
        agentObject.decrementRequestCount(parsedLinks.size());
        linksHash.putAll(parsedLinks);
        }
        return linksHash;
    }

    public Hashtable<Integer, Hashtable<String, Object>> getLinksFromRankingAgent() throws IOException {
        agentsHandler.checkIfAgentsAlive();
        Hashtable<Integer, Hashtable<String, Object>> linksHash = new Hashtable<>();
        for (AgentObject agentObject: agentsHandler.getRankingAgentList()) {
            String response = HttpRequestClass.sendGETRequest(agentObject.getCommunicationUrl());
            Hashtable<Integer, Hashtable<String, Object>> parsedLinks = JsonParsingClass.parseRankedLinks(response);
            agentObject.decrementRequestCount(parsedLinks.size());
            linksHash.putAll(parsedLinks);
        }
        return linksHash;
    }

    public void sendAllRawReadyLinks() throws IOException, InterruptedException {
        if (!agentsHandler.getCircularAgentsList().isEmpty()) {
            agentsHandler.checkIfAgentsAlive();
            AgentObject agentObject = agentsHandler.getCircularAgentMinimumRequestCount();
            String url = agentObject.getCommunicationUrl();
            Integer linksAmount = this.rawLinksHandler.sendAllRawReadyLinks(queryThreadsList, url);
            agentObject.incrementRequestCount(linksAmount);
        }
    }

    public void sendReadyCircularLinks() throws IOException, InterruptedException {
        if (!agentsHandler.getRankingAgentList().isEmpty()) {
            agentsHandler.checkIfAgentsAlive();
            AgentObject agentObject = agentsHandler.getRankingAgentMinimumRequestCount();
            String url = agentObject.getCommunicationUrl();
            Integer linksAmount = this.rankedLinksHandler.sendAllCircularLinks(queryThreadsList, url);
            agentObject.incrementRequestCount(linksAmount);
        }
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
        AgentsHandler agentsHandler = new AgentsHandler();
        ApplicationManager applicationManager = new ApplicationManager(agentsHandler);
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
