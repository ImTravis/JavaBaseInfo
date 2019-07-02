package com.example.demo.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明
 */
public class GetClass {

    public static void main(String[] args){
        try{
            Class appClass= Class.forName("com.example.demo.reflection.Apple");
            Constructor constructor = appClass.getConstructor(String.class,int.class);
            Method getPrice = appClass.getMethod("getPrice");

            Object  appObj = constructor.newInstance("苹果",2);

            System.out.print("\n"+getPrice.invoke(appObj));


            Class  appClass2 = Apple.class;
            Apple apple = (Apple)appClass2.newInstance();//类必须要有空构造函数
            apple.setPrice(2);
            System.out.print("\n"+apple.getPrice());
            for(Field e :appClass2.getFields()){
                System.out.print("\n"+e.getName());
            }


            Class  appClass3= Apple.class;
            Constructor constructor3 = appClass.getConstructor(String.class,int.class);
            Apple appl3e = (Apple)constructor3.newInstance("桃子",4);

            System.out.print("\n"+appl3e.getPrice());

        }catch (Exception e){
            e.getStackTrace();
            System.out.print(e.getLocalizedMessage());
        }
    }
}
