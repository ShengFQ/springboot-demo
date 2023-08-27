package io.shengfq.aop.common;

import org.apache.log4j.Logger;

public class LoggerUtils {

    private static Logger logger;

    public static Logger getLogger() {
        if (logger == null) {
            logger = Logger.getLogger("SpringBootDemoLogs");
        }
        return logger;
    }
}
