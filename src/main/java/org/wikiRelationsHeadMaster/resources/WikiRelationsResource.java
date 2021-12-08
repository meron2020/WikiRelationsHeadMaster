package org.wikiRelationsHeadMaster.resources;

import org.wikiRelationsHeadMaster.core.LinksObject;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;

@Path("/wiki_relations")
public class WikiRelationsResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Integer getWikiRelationsResource() {
        Random random = new Random();

        return random.nextInt(5000);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Object postQuery(String query) {
        return null;
    }

}
