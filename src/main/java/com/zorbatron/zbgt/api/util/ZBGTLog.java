package com.zorbatron.zbgt.api.util;

import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class ZBGTLog {

    public static Logger logger;

    public static void init(@NotNull Logger modLogger) {
        logger = modLogger;
    }
}
