package com.example.demo;

import com.example.demo.event.HelloEvent;
import com.example.demo.extend.HelloWorldImpl;
import com.example.demo.extend.HelloWorldImplChild;
import com.example.demo.extend.HelloWorldImplChildChild;
import org.apache.catalina.core.ApplicationContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    HelloWorldImplChildChild helloWorldImplChild;



    //继承
    @Test
    public void contextLoads() {
        HelloWorldImpl littlechild = new HelloWorldImplChild();
        HelloWorldImpl helloWorldImplChildChild = new HelloWorldImplChildChild();
        helloWorldImplChildChild.sayHello();

        //想通过父类 来调用子类的方法，该子类必须重写了父类的方法；（父类方法不能为私有）
        HelloWorldImplChild helloWorldImplChild = new HelloWorldImplChild();
        //错误写法：
//        HelloWorldImpl helloWorldImplChild = new HelloWorldImplChild();
        helloWorldImplChild.sayPrivate();



        //不能直接调用 类的私有方法
//        HelloWorldImpl helloWorld = new HelloWorldImpl();
//        helloWorld.getPri();

        System.out.print(Math.round(0.5));
        System.out.print(Math.round(0.4));
        System.out.print(Math.round(-1.4)+"\n");
        System.out.print(Math.round(-1.5));
        System.out.print(Math.round(-1.6)+"\n");


    }

}
