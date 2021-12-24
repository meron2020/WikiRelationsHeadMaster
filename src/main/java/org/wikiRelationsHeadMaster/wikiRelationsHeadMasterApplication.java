package org.wikiRelationsHeadMaster;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.wikiRelationsHeadMaster.core.ApplicationManager;
import org.wikiRelationsHeadMaster.resources.FrontPageResource;
import org.wikiRelationsHeadMaster.resources.WikiRelationsResource;

import java.util.ArrayList;

public class wikiRelationsHeadMasterApplication extends Application<wikiRelationsHeadMasterConfiguration> {

    public static void main(final String[] args) throws Exception {
        new wikiRelationsHeadMasterApplication().run(args);
    }

    @Override
    public String getName() {
        return "wikiRelationsHeadMaster";
    }

    @Override
    public void initialize(final Bootstrap<wikiRelationsHeadMasterConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final wikiRelationsHeadMasterConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
        ArrayList<Integer> idArrayList = new ArrayList<>();
        ApplicationManager applicationManager = new ApplicationManager();
        new Thread(applicationManager).start();
        environment.jersey().register(new WikiRelationsResource(applicationManager, idArrayList));
        environment.jersey().register(new FrontPageResource(idArrayList));
    }

}
