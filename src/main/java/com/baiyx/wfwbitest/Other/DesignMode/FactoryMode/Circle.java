package com.baiyx.wfwbitest.Other.DesignMode.FactoryMode;

import com.baiyx.wfwbitest.Other.DesignMode.FactoryMode.Shape;

/**
 * @Author: baiyx
 * @Date: 2023年9月28日, 0028 上午 9:22:22
 * @Description: 工厂模式: 定义图形接口的实现类圆形类
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("画出了一个圆形!");
    }
}
