package com.bat.designPattern.Strategy.impl;

import com.bat.designPattern.Strategy.BasePromotion;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2021/2/4 16:27
 * @description: 满减
 **/
public class PriceBreakImpl extends BasePromotion {
    private Double condition;
    private Double returnMoney;

    public PriceBreakImpl(Double condition, Double returnMoney) {
        this.condition = condition;
        this.returnMoney = returnMoney;
    }

    @Override
    public Double sale(Double money) {
        if (money > condition) {
            return money - (money / condition) * returnMoney;
        }
        return money;
    }
}
