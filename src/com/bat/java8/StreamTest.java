package com.bat.java8;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2021/2/19 15:31
 **/
public class StreamTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("张无忌");
        list.add("周芷若");
        list.add("赵敏");
        list.add("张强");
        list.add("张三丰");
        list.stream().filter(s -> s.startsWith("张")).filter(s -> s.length() == 3).map(String::length).forEach(System.out::println);

    }
}
