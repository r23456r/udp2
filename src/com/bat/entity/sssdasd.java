package com.bat.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2020/8/19 18:10
 **/
public class sssdasd {
    public static void main(String[] args) {

        List<Event> events = new ArrayList<>(12);
        for (int i = 0; i < 10000000; i++) {
            Event event = new Event();
            event.setUid(String.valueOf(i));
            events.add(event);
        }
        Event[] events1 = events.toArray(new Event[0]);
        List<Event> list = Arrays.asList(events1);
        //恒安暂定单次100条
        int size = 100;
        for (List<Event> integers : ArraysUtils.averageAssign(list, size)) {
            Event[] array = integers.toArray(new Event[0]);
            for (int i = 0; i < array.length; i++) {
                System.out.print(array[i].getUid());
            }
            System.out.println("size"+integers.size());
            System.out.println("----------------");
        }
    }


}
