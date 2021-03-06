package com.example.demo.proxy.jdkproxy;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * JDK动态代理类 （有接口）
 */
// 步骤 ：TODO 获取代理类（接口 CLASS）->通过反射获取代理类的构造方法（入参InvocationHandler 处理器）
// TODO -> 通过构造方法获取InvocationHandler实例对象（参数 接口实现类）-> 通过代理类的构造方法获取代理类实例
public class JDKProxyTest {
    //动态代理
    //学习地址：https://www.ibm.com/developerworks/cn/java/j-lo-proxy1/
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // 这里有两种写法，采用复杂的一种写法，有助于理解。

        //TODO:通过Proxy类的getProxyClass方法获取动态代理类的类对象(区别于委托类)
        Class<?> proxyClass = Proxy.getProxyClass(JDKProxyTest.class.getClassLoader(), HelloWorldProxy.class);
        //TODO:通过反射机制获取代理类的构造方法，方法签名为getConstructor(InvocationHandler.class)
        final Constructor<?> cons = proxyClass.getConstructor(InvocationHandler.class);

        //TODO:将目标对象(被代理的实现类)作为参数传入，通过构造方法获取自定义的InvocationHandler实例对象
        final InvocationHandler ih = new MyInvocationHandler(new HelloworldProxyImpl());
        //TODO:将自定义的InvocationHandler实例对象作为参数传入，通过代理类的构造方法获取代理类实例
        HelloWorldProxy helloWorld = (HelloWorldProxy) cons.newInstance(ih);
        helloWorld.sayBye();

        // 下面是简单的一种写法，本质上和上面是一样的
        //该方法用于为[指定类装载器]、[一组接口]、[调用处理器]生成动态代理类实例
//        HelloWorldProxy helloWorld2=(HelloWorldProxy)Proxy.
//                 newProxyInstance(JDKProxyTest.class.getClassLoader(),
//                        new Class<?>[]{HelloWorldProxy.class},
//                        new MyInvocationHandler(new HelUnicastRemoteObject loworldProxyImpl()));
//        helloWorld2.sayHello();

        List<String> a = new ArrayList<>();
        a.add("a");
        a.add("b");
        a.set(1,"c");
        System.out.print("***"+a.toString());

    }

}