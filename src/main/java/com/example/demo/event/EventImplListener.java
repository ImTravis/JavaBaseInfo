package com.example.demo.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明
 */
public interface EventImplListener<E extends ApplicationEvent> extends EventInit {
    void onApplicationEvent(E var1);

}
