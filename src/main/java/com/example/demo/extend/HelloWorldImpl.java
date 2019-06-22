package com.example.demo.extend;

import org.springframework.stereotype.Service;

/**
 * @Author xcc
 * @Description //TODO $
 * @Date $ $
 **/
@Service
public class HelloWorldImpl implements HelloWorld{
    @Override
    public void sayHello() {
        System.out.print("HelloWorldImpl:say hello\n");
    }

    @Override
    public void sayBye() {

    }

    private final void sayPrivate(){
        System.out.print("HelloWorldImpl：say:private\n");

    }
    private  void getPri(){
        System.out.print("HelloWorldImpl：say:private\n");

    }


}
