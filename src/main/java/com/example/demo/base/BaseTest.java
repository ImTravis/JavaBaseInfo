package com.example.demo.base;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明: Java 实现字符串反转,List<>反转
 */
public class BaseTest {

    public static void main(String[]  args){
//        System.out.print(reverseString("hello")+"\n");
//        System.out.print(reverse("hello my name is travis")+"\n");
//        iteratorHashMap();
//        iteratorList();
        iteratorArrayList();

    }

    //字符串倒序
    public static String reverseString(String value){
        List<String> a = Arrays.asList(value.split(""));
        Collections.reverse(a);
        String b ="";
        for(String e : a){
            b+=e;
        }
        return b;
    }
    //字符串倒序
    public static String reverse(String str){
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * 遍历HashMap的四种方法
     */
    public static void iteratorHashMap(){
        Map<String,String> map = new HashMap();
        map.put("one","临沂");
        map.put("two","上海");
        map.put("three","南昌");
        map.put("four","江西");

        //第一种：普通使用，二次取值
        System.out.println("\n通过Map.keySet遍历key和value：");
        for(String key:map.keySet())
        {
            System.out.println("Key: "+key+" Value: "+map.get(key));
        }

        //第二种
        System.out.println("\n通过Map.entrySet使用iterator遍历key和value: ");
        Iterator map1it=map.entrySet().iterator();
        while(map1it.hasNext())
        {
            Map.Entry<String, String> entry=(Map.Entry<String, String>) map1it.next();
            System.out.println("Key: "+entry.getKey()+" Value: "+entry.getValue());
        }

        //第三种：推荐，尤其是容量大时
        System.out.println("\n通过Map.entrySet遍历key和value");
        for(Map.Entry<String,String> entry: map.entrySet())
        {
            System.out.println("Key: "+ entry.getKey()+ " Value: "+entry.getValue());
        }

        //第四种
        System.out.println("\n通过Map.values()遍历所有的value，但不能遍历key");
        for(String v:map.values())
        {
            System.out.println("The value is "+v);
        }
    }

    public static void iteratorList(){
        String[] name = {
                "张三","李四","王二"
        };
        List<String> nameList = Arrays.asList(name);
        System.out.print("\n容量："+nameList.size());

        Iterator<String> nameIter = nameList.iterator();
        while(nameIter.hasNext()){
            System.out.print(nameIter.next());
        }


        System.out.print("\n容量："+nameList.size());
    }

    public static void iteratorArrayList(){
        String[] name = {
                "张三","李四","王二"
        };
        List<String> arrayList =new ArrayList<String>();
        arrayList = Arrays.asList(name);
        System.out.print("\n容量："+arrayList.size());

        Iterator<String> nameIter = arrayList.iterator();
        while(nameIter.hasNext()){
            System.out.print("\n"+nameIter.next());
        }
        System.out.print("\n容量："+arrayList.size());

        ArrayList<String> list = new ArrayList<>(10);

        list.add("a");
        list.add("b");
        list.add("c");
        list.ensureCapacity(16);
        System.out.print("\n容量："+ getArrayListCapacity(list));
        list.trimToSize();
        System.out.print("\n容量："+ getArrayListCapacity(list));
        System.out.print("\n位置："+ list.indexOf("a"));
        System.out.print("\n内容："+ list);
    }

    public static int getArrayListCapacity(ArrayList<?> arrayList) {
        Class<ArrayList> arrayListClass = ArrayList.class;
        try {
            //获取 elementData 字段
            Field field = arrayListClass.getDeclaredField("elementData");
            //开始访问权限
            field.setAccessible(true);
            //把示例传入get，获取实例字段elementData的值
            Object[] objects = (Object[])field.get(arrayList);
            //返回当前ArrayList实例的容量值
            return objects.length;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
