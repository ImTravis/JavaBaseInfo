package com.example.demo.abstractdo;

/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明
 */
public class OptionActionChild extends OptionAction {
    private String animal;
    @Override
    public void getAbsHello(String option) {
        System.out.print(animal+"："+option);
    }


    //抽象类 有空构造方法 子类可继承，可不继承，如果抽象类只有 有参构造方法，那必须实现并继承父类的有参构造方法
    public OptionActionChild(String option, String animal) {
        super(option);//必须第一行

        this.animal = animal;
    }
//    public OptionActionChild(){
//
//    }

    //非抽象方法可重写，可不重写
    public void getHello(){
        System.out.print("action child gethello\n");
    }


    public static void main(String[] args){
        OptionAction optionAction = new OptionActionChild("大笑","熊猫");
        optionAction.getHello();

    }

}
