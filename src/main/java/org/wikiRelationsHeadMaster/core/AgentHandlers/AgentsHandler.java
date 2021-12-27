package org.wikiRelationsHeadMaster.core.AgentHandlers;

import org.wikiRelationsHeadMaster.core.HeadMasterObjects.AgentObject;

import java.util.ArrayList;
import java.util.Objects;

public class AgentsHandler {
    ArrayList<AgentObject> circularAgentsList = new ArrayList<>();
    ArrayList<AgentObject> rankingAgentList = new ArrayList<>();

    public void addCircularAgent(AgentObject agentObject) {
        circularAgentsList.add(agentObject);
    }

    public void addRankingAgentList(AgentObject agentObject) {
        rankingAgentList.add(agentObject);
    }

    public void addAgent(AgentObject agentObject) {
        if (Objects.equals(agentObject.getType(), "Circular")) {
            circularAgentsList.add(agentObject);
        }
        if (Objects.equals(agentObject.getType(), "Ranking")) {
            rankingAgentList.add(agentObject);
        }
    }

    public void removeAgent(AgentObject agentObject) {
        if (Objects.equals(agentObject.getType(), "Circular")) {
            circularAgentsList.remove(agentObject);
        }
        if (Objects.equals(agentObject.getType(), "Ranking")) {
            rankingAgentList.remove(agentObject);
        }
    }

    public AgentObject getCircularAgentMinimumRequestCount() {
        AgentObject minimumRequestCountObject = circularAgentsList.get(0);
        for (AgentObject agentObject: circularAgentsList) {
            if (agentObject.getRequestCount() < minimumRequestCountObject.getRequestCount()) {
                minimumRequestCountObject = agentObject;
            }
        }
        return minimumRequestCountObject;
    }

    public AgentObject getRankingAgentMinimumRequestCount() {
        AgentObject minimumRequestCountObject = rankingAgentList.get(0);
        for (AgentObject agentObject: rankingAgentList) {
            if (agentObject.getRequestCount() < minimumRequestCountObject.getRequestCount()) {
                minimumRequestCountObject = agentObject;
            }
        }
        return minimumRequestCountObject;
    }
}
