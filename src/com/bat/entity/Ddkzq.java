package com.bat.entity;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2019/12/2 14:28
 **/

import lombok.Data;

import java.util.List;

/**
 * 单灯控制器
 */
@Data
public class Ddkzq {

    /**
     * 控制口属性信息
     */
    private List<Kzksx> kzksx;

}
