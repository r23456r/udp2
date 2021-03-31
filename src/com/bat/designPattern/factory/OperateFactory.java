package com.bat.designPattern.factory;

/**
 * @author zhangyuhang
 * 计算工厂类
 */
public class OperateFactory {
    public Operation getOperation(String operate) {

        switch (operate) {
            case "+":
                return new OperateAdd();
            case "*":
                return new OperateMult();
            case "/":
                return new OperateDiv();
            default:
                throw new IllegalArgumentException("不支持此运算符");
        }
    }
}
