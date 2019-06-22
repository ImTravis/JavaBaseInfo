package com.example.demo.proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author xcc 调用处理器
 * @Description
 * //TODO target属性表示代理的目标对象。
 * InvocationHandler是连接代理对象与目标对象的自定义中间类,MyInvocationHandler必须实现的接口，
 * 只有一个方法public Object invoke(Object proxy, Method method, Object[] args)。
 * 在这个方法中，Proxy通过newProxyInstance方法创建代理对象，method表示目标对象被调用的方法，args表示目标对象被调用方法的形参列表。
 * @Date $ $
 **/

//TODO 1 通过实现InvocationHandler接口来自定义自己的InvocationHandler。
//每次生成动态代理类对象时都需要指定一个实现了该接口的调用处理器对象
public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    //java.lang.reflect.InvocationHandler：这是调用处理器接口，
    // 它自定义了一个 invoke 方法，用于集中处理在动态代理类对象上的方法调用，通常在该方法中实现对委托类的代理访问。
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("method :" + method.getName() + " is invoked!");
        return method.invoke(target, args); // 执行相应的目标方法
    }
}
