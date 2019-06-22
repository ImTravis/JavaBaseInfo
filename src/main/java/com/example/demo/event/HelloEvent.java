package com.example.demo.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明
 */
public class HelloEvent extends ApplicationEvent {
    private String name;

    public HelloEvent(Object source,String name) {
        super(source);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
