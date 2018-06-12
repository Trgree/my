package org.ace.example.debug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author L
 * @date 2018/6/12
 */
public class DebugTest {
    static final Logger logger = LoggerFactory.getLogger(DebugTest.class);

    public static void test(){
        logger.debug("test debug ...");
    }
}
