package org.wikiRelationsHeadMaster.core.AgentCommunicationThreads;

import org.wikiRelationsHeadMaster.core.ApplicationManager;
import org.wikiRelationsHeadMaster.core.QueryThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class CircularAgentCommsThread implements Runnable {
    ApplicationManager applicationManager;

    public CircularAgentCommsThread(ApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
    }


    @Override
    public void run() {
        while (true) {
            try {
                Hashtable<Integer, Hashtable<String, Object>> circularLinks;
                applicationManager.sendAllRawReadyLinks();
                Thread.sleep(5000);
                circularLinks = applicationManager.getLinksFromAgent();
                System.out.println("[+] Returned links from circular links agent.");
                if (circularLinks.size() == 0) {
                    continue;
                }

                ArrayList<QueryThread> newQueryThreadList = applicationManager.rawLinksHandler.updateAllQueryThreads(circularLinks);

                applicationManager.setQueryThreadsList(newQueryThreadList);
            }
             catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

//            System.out.println(circularLinks);
            }

        }


    }

