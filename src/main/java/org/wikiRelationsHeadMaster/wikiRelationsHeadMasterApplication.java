package org.wikiRelationsHeadMaster;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
    }

}
