package com.bat.designPattern.factory;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2021/2/1 15:36
 **/
public class OperateDiv  implements Operation {
    @Override
    public double getResult(double num1, double num2) {
        if (num2 == 0) {
            throw new IllegalArgumentException("除数不能为0");
        }
        return num1 / num2;
    }
}
