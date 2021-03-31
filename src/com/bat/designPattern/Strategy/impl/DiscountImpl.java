package com.bat.designPattern.Strategy.impl;

import com.bat.designPattern.Strategy.BasePromotion;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2021/2/4 16:15
 * @description: 折扣
 **/
public class DiscountImpl extends BasePromotion {

    private Double rate = 1D;

    public DiscountImpl(Double rate) {
        this.rate = rate;
    }

    @Override
    public Double sale(Double money) {

        return money + money * rate;
    }
}
