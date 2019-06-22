package com.example.demo.proxy.jdkproxy;

/**
 * @Author xcc
 * @Description //TODO $
 * @Date $ $
 **/
public class HelloworldProxyImpl implements HelloWorldProxy {
    @Override
    public void sayHello() {
        System.out.print("hello world");
    }

    @Override
    public void sayBye() {
        System.out.print("sayBye");
    }
}
