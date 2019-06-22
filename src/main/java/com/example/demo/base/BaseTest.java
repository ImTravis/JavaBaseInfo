package com.example.demo.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明: Java 实现字符串反转,List<>反转
 */
public class BaseTest {

    public static void main(String[]  args){
        System.out.print(reverseString("hello")+"\n");
        System.out.print(reverse("hello my name is travis")+"\n");
        int j = (9) >>> 1;
        System.out.print(j+"\n");

    }

    public static String reverseString(String value){
        List<String> a = Arrays.asList(value.split(""));
        Collections.reverse(a);
        String b ="";
        for(String e : a){
            b+=e;
        }
        return b;
    }
    public static String reverse(String str){
        return new StringBuilder(str).reverse().toString();
    }

}
