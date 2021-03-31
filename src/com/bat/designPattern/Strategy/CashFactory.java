package com.bat.designPattern.Strategy;

import com.bat.designPattern.Strategy.impl.DiscountImpl;
import com.bat.designPattern.Strategy.impl.PriceBreakImpl;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2021/2/4 17:07
 **/
public class CashFactory {
    private BasePromotion basePromotion;

    public BasePromotion createCashBean(String type) {
        switch (type) {
            case "正常":
                basePromotion = new DiscountImpl(1.0);
                break;
            case "300-100":
                basePromotion = new PriceBreakImpl(300.0, 100.0);
                break;
            default:
                break;
        }
        return basePromotion;
    }
}
