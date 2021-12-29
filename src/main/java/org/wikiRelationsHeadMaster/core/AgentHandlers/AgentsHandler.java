package org.wikiRelationsHeadMaster.core.AgentHandlers;

import org.wikiRelationsHeadMaster.core.HeadMasterObjects.AgentObject;

import java.util.ArrayList;
import java.util.Objects;

public class AgentsHandler {
    ArrayList<AgentObject> circularAgentsList = new ArrayList<>();
    ArrayList<AgentObject> rankingAgentList = new ArrayList<>();

    public void checkIfAgentsAlive() {
        for (AgentObject circularAgent: circularAgentsList) {
            if (AgentHealthChecker.checkIfAgentAlive(circularAgent.getUrl())) {
                this.removeAgent(circularAgent);
            };
        }
        for (AgentObject rankingAgent: rankingAgentList) {
            if (AgentHealthChecker.checkIfAgentAlive(rankingAgent.getUrl())) {
                this.removeAgent(rankingAgent);
            }
        }
    }

    public ArrayList<AgentObject> getCircularAgentsList() {
        return circularAgentsList;
    }

    public ArrayList<AgentObject> getRankingAgentList() {
        return rankingAgentList;
    }

    public void addCircularAgent(AgentObject agentObject) {
        circularAgentsList.add(agentObject);
    }

    public void addRankingAgentList(AgentObject agentObject) {
        rankingAgentList.add(agentObject);
    }

    public void addAgent(AgentObject agentObject) {
        if (Objects.equals(agentObject.getType(), "links")) {
            circularAgentsList.add(agentObject);
        }
        if (Objects.equals(agentObject.getType(), "ranking")) {
            rankingAgentList.add(agentObject);
        }
    }

    public void removeAgent(AgentObject agentObject) {
        if (Objects.equals(agentObject.getType(), "links")) {
            circularAgentsList.remove(agentObject);
        }
        if (Objects.equals(agentObject.getType(), "ranking")) {
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
