package com.bat.entity;

import java.util.HashMap;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2020/9/23 18:52
 **/
public class Map {
    public static void main(String[] args) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("15", "111");
        System.out.println(map.get("15"));
    }
}
