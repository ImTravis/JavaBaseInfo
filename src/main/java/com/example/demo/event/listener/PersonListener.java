package com.example.demo.event.listener;


/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明:PersonListener(事件监听器)
 */
public interface PersonListener {

    void getEat(PersonEvent event);


    void getDrink(PersonSource source);
}
