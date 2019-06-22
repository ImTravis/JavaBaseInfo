package com.example.demo.event.listener;

/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明:事件源
 */
public class PersonSource {

    private String name;

    private String phone;

    private PersonListener listener;

    public PersonSource(String name) {
        this.name = name;
    }

    public void eat() {

        System.out.print("小狗准备就绪\n");
        if (listener != null) {
            /**
             * 调用监听器的doeat方法监听Person类对象eat(吃)这个动作，将事件对象Event传递给doeat方法，
             * 事件对象封装了事件源，new Event(this)中的this代表的就是事件源
             */
            listener.getEat(new PersonEvent(this));
        }
    }

    public void drink() {
        System.out.print("小狗准备就绪\n");
        if (listener != null) {
            /**
             * 调用监听器的doeat方法监听Person类对象eat(吃)这个动作，将事件对象Event传递给doeat方法，
             * 事件对象封装了事件源，new Event(this)中的this代表的就是事件源
             */
            listener.getDrink(this);
        }
    }

    public void registerListener(PersonListener personListener){
        this.listener = personListener;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
