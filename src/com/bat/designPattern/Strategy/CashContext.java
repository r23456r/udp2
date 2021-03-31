package com.bat.designPattern.Strategy;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2021/2/20 10:29
 **/
public class CashContext {
    private CashFactory cashFactory;

    public CashContext(CashFactory cashFactory) {
        this.cashFactory = cashFactory;
    }
}
