package com.baiyx.wfwbitest.Other.Demo;

/**
 * @Author: baiyx
 * @Date: 2023年6月30日, 0030 下午 2:12:53
 * @Description: 研究继承关系的程序执行顺序: 测试类
 */

public class DemoTest1 {

    public static void main(String[] args){

        ParentInterface1 parentInterface1 = new ParentClass1();
        parentInterface1.parentImplementsMethmod();

        System.out.println();

        ParentInterface1 parentInterface2 = new ChildClass1();
        parentInterface2.parentImplementsMethmod();
    }
}
