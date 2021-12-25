package org.wikiRelationsHeadMaster.resources;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.Random;

@Path("/")
public class FrontPageResource {
    ArrayList<Integer> idArrayList;
    public FrontPageResource(ArrayList<Integer> idArrayList) {
        this.idArrayList = idArrayList;
    }

    @GET
    public Integer getWikiRelationsResource() {
        Random random = new Random();
        Integer number = random.nextInt(5000);
        while (true) {
            if (!idArrayList.contains(number)) {
                idArrayList.add(number);
                break;
            }
        }
        return number;
    }
}
