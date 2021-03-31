package com.bat.designPattern.factory;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2021/2/1 15:24
 **/
public class CalServer {

    OperateFactory factory = new OperateFactory();

    public double base(double num1, double num2, String operate) {
        Operation operation = factory.getOperation(operate);
        return operation.getResult(num1, num2);
    }
}
