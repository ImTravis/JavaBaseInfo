package com.example.demo.proxy.cglibproxy;

/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明
 */
public class CglibTest {

    public static void main(String[] args) {
        CglibProxy cglibProxy=new CglibProxy();
        //绑定关系，返回的是StudentService的代理对象
        StudentServiceImpl studentService=(StudentServiceImpl)cglibProxy.getProxy(StudentServiceImpl.class);
        //执行代理对象的intercept（）方法，而不是StudentService中的study（）方法。
        //CGLIB的具体实现没看，猜测可能使用了多态，enhancer.create() 返回的真实对象的子类，增强的逻辑在子类中。
        studentService.study();
    }
}
