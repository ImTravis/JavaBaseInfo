package com.example.demo.reflection;

/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明
 */
public class Apple {

    private String name;

    private int price;

    String aa;

    public String getAa() {
        return aa;
    }

    public void setAa(String aa) {
        this.aa = aa;
    }

    public Apple() {
    }

    public Apple(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
