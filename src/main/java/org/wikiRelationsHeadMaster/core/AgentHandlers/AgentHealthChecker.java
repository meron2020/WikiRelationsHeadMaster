package org.wikiRelationsHeadMaster.core.AgentHandlers;

import org.wikiRelationsHeadMaster.core.networking.HttpRequestClass;

import java.io.IOException;

public class AgentHealthChecker {
    public static boolean checkIfAgentAlive(String url) {
        try {
            HttpRequestClass.sendGETRequest(url);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
