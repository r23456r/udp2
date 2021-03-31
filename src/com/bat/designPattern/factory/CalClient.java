package com.bat.designPattern.factory;

import java.util.Scanner;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2021/2/1 15:25
 **/
public class CalClient {


    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("请输入第一个数");
        double aDouble = Double.parseDouble(s.next());
        System.out.println("请输入第二个数");
        double bDouble = Double.parseDouble(s.next());
        System.out.println("请输入运算符");
        String str = s.next();
        CalServer server = new CalServer();
        System.out.println("计算结果：" + server.base(aDouble, bDouble, str));
    }
}
