package com.example.demo.extend;

import org.springframework.stereotype.Component;

/**
 * @Author xcc
 * @Description //TODO $
 * @Date $ $
 **/
@Component
public class HelloWorldImplChildChild extends HelloWorldImplChild {

    public void sayHello() {
        System.out.print("HelloWorldImplChildChild:say hello\n");

    }
}
