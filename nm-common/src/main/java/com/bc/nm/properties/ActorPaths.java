package com.bc.nm.properties;

import static com.bc.nm.properties.ActorNames.BASE_ACTOR;
import static com.bc.nm.properties.ActorNames.SITE_POLLING_ACTOR;

public class ActorPaths {

    private ActorPaths() {
    }

    public static final String SYSTEM_NAME = "NetStat";
    private static final String BASE_PATH = String.format("akka://%s/%s", SYSTEM_NAME, BASE_ACTOR);

    // actor paths
    public static final String SITEPINGER_ACT_PATH = String.format("%s/%s", BASE_PATH, SITE_POLLING_ACTOR);
}
