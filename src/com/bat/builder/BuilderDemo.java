package com.bat.builder;

import com.bat.common.User;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2021/1/27 16:55
 **/
public class BuilderDemo {
    public static void main(String[] args) {
        User user = Builder.of(User::new).with(User::setAge, 1)
                .with(User::setName, "1").build();
        System.out.println(user.toString());
    }
}
