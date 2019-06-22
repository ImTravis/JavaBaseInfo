package com.example.demo.abstractdo;

import com.example.demo.extend.HelloWorld;

/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明:抽象类
 */
public abstract class OptionAction{

    private String option;

    public OptionAction(String option) {
        this.option = option;
    }

//    public OptionAction() {
//    }


    public void getHello(){

        System.out.print("action get hello");

    }

    public abstract void getAbsHello(String option);
}
