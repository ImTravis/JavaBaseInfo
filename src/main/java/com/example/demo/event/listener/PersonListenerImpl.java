package com.example.demo.event.listener;

/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明
 */
public class PersonListenerImpl implements PersonListener {
    @Override
    public void getEat(PersonEvent event) {
        PersonSource source = event.getSource();
        System.out.print(source.getName()+"实现类\n");
    }

    @Override
    public void getDrink(PersonSource source) {
        System.out.print(source.getName()+"实现类\n");
    }
}
