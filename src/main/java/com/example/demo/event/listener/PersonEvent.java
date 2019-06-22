package com.example.demo.event.listener;

/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明:事件对象
 */
public class PersonEvent {

    private PersonSource source;//事件源(Person就是事件源)

    public PersonEvent(){

    }

    public PersonEvent(PersonSource source) {
        this.source = source;
    }

    public PersonSource getSource() {
        return source;
    }

    public void setSource(PersonSource source) {
        this.source = source;
    }
}
