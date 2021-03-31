package com.bat.entity;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2019/12/2 14:32
 **/

import lombok.Data;

/**
 * 控制口属性
 */
@Data
public class Kzksx {
    /**
     * 路数
     */
    private Integer ls;

    /**
     * 开闭状态  0：down; 1：link
     */
    private Integer kbzt;

    /**
     * 电压 (精确到小数点一位)
     */
    private Float dy;

    /**
     * 电流 (精确到小数点两位)
     */
    private Float dl;

    /**
     * POE状态
     */
    private Integer poezt;

}
