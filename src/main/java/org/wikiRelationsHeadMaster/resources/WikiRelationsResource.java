package org.wikiRelationsHeadMaster.resources;

import org.wikiRelationsHeadMaster.core.ApplicationManager;
import org.wikiRelationsHeadMaster.core.HeadMasterObjects.UserObject;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Path("/wiki_relations")
public class WikiRelationsResource {
    ApplicationManager applicationManager;
    ArrayList<Integer> idArrayList;

    public WikiRelationsResource(ApplicationManager applicationManager, ArrayList<Integer> idArrayList) {
        this.applicationManager = applicationManager;
        this.idArrayList = idArrayList;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Object getWikiRelationsResource(UserObject userObject) {
        ArrayList<HashMap<String, String>> ranked = applicationManager.getRankedLinksById(userObject.getId());
            if (!ranked.isEmpty()) {
                this.idArrayList.remove(userObject.getId());
                return ranked;
            }
            else {
                return null;
            }

        }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Object postQuery(UserObject userObject) throws InterruptedException, IOException {
        applicationManager.addThread(userObject.getQuery(), userObject.getId());
        return "Added query thread";
    }
}