package com.example.demo.extend;

import org.springframework.stereotype.Component;

/**
 * @Author xcc
 * @Description //TODO $
 * @Date $ $
 **/
@Component
public class HelloWorldImplChild extends HelloWorldImpl {

    public void sayHello() {
        System.out.print("HelloWorldImplChild:say hello\n");
    }

    public void sayPrivate(){
        System.out.print("HelloWorldImplChild：say:private\n");
    }


}
