package com.example.demo.event.listener;

/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明
 */
public class PersonTest {

    public static void main(String[] args){
        PersonSource p = new PersonSource("小狗a ");
        PersonListener personListener = new PersonListenerImpl();
        //注册监听对象
        p.registerListener(personListener);
        p.drink();

    }
}
