package org.wikiRelationsHeadMaster.resources;

import org.wikiRelationsHeadMaster.core.AgentHandlers.AgentsHandler;
import org.wikiRelationsHeadMaster.core.HeadMasterObjects.AgentObject;

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
    public String postNewAgent(@Context UriInfo uriInfo, AgentObject agentObject) {
        agentObject.setUrl(uriInfo.getPath());
        agentObject.setRequestCount(0);
        agentsHandler.addAgent(agentObject);
        return "Agent info received";
    }


}
