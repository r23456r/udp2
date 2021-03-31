package com.bat.designPattern.factory;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2021/2/1 15:36
 **/
public class OperateAdd implements Operation {
    @Override
    public double getResult(double num1, double num2) {
        return num1 + num2;
    }
}
