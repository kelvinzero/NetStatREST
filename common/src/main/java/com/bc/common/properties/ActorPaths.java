package com.bc.common.properties;

import static com.bc.common.properties.ActorNames.BASE_ACTOR;
import static com.bc.common.properties.ActorNames.SITEPOLLING_ACTOR;

public class ActorPaths {


    public static final String SYSTEM_NAME = "NetStat";
    private static final String BASE_PATH = String.format("akka://NetMonitor/%s", BASE_ACTOR);

    // actor paths
    public static final String SITEPINGER_ACT_PATH = String.format("%s/%s", BASE_PATH, SITEPOLLING_ACTOR);
}
