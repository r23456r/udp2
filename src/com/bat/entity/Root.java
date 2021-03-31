package com.bat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2019/12/2 14:22
 **/
public class Root {

    /**
     * 设备标识
     */
    private String sbbs;
    /**
     *时钟
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sz;
    /**
     *单灯控制器
     */

    private Ddkzq ddkzq;
    /**
     * 网口
     */
    private Wk wk;

}
