package org.wikiRelationsHeadMaster.resources;

import org.wikiRelationsHeadMaster.core.AgentHandlers.AgentsHandler;
import org.wikiRelationsHeadMaster.core.HeadMasterObjects.AgentObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/communication")
public class HMCommunicationsResource {
    AgentsHandler agentsHandler;

    public HMCommunicationsResource(AgentsHandler agentsHandler) {
        this.agentsHandler = agentsHandler;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String postNewAgent(@Context HttpServletRequest request, AgentObject agentObject) {
        agentObject.setUrl(request.getRemoteAddr() + ":" + agentObject.getPort());
        agentObject.setRequestCount(0);
        agentsHandler.addAgent(agentObject);
        System.out.println(agentsHandler.getCircularAgentsList().size());
        System.out.println(agentsHandler.getRankingAgentList().size());
        return "Agent info received";
    }


}
