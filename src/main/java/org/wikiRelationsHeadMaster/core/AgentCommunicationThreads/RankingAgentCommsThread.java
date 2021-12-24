package org.wikiRelationsHeadMaster.core.AgentCommunicationThreads;

import org.wikiRelationsHeadMaster.core.ApplicationManager;
import org.wikiRelationsHeadMaster.core.QueryThread;

import java.io.IOException;
import java.util.Hashtable;

public class RankingAgentCommsThread implements Runnable {
    public ApplicationManager applicationManager;

    public RankingAgentCommsThread(ApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
    }


    @Override
    public void run() {
        try {
            while (true) {
                applicationManager.sendReadyCircularLinks();

                Hashtable<Integer, Hashtable<String, Object>> topTenRanked;
                Thread.sleep(5000);
                applicationManager.sendReadyCircularLinks();
                System.out.println("[+] Sent ready circular links.");
                topTenRanked = applicationManager.getLinksFromRankingAgent();
                System.out.println("[+] Returned links from ranking agent.");
                if (topTenRanked.isEmpty()) {
                    continue;
                }
                applicationManager.setQueryThreadsList(applicationManager.rankedLinksHandler.updateAllQueryThreads(topTenRanked));
                for (QueryThread queryThread: applicationManager.queryThreadsList) {
                    System.out.println(queryThread.getLinksObject().getTopTenLinks());}
            }

        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
