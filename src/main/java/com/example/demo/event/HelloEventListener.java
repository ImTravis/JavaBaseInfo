package com.example.demo.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明
 */
@Component
public class HelloEventListener implements EventImplListener<HelloEvent> {
    private static final Logger logger = LoggerFactory.getLogger(HelloEventListener.class);


    @Override
    public void onApplicationEvent(HelloEvent var1) {
        logger.info("receive {} say hello!",var1.getName());

    }

    @Override
    public void content(String option) {
        logger.info("receive {} say hello!",option);
    }
}
