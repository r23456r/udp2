package com.bat;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2020/11/18 18:21
 **/
public class sss {
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(new String(Objects.requireNonNull(ByteUtil.hexStringToBytes("18697e035cd4489cb83e0d551ba0ddfa")),"UTF-8"));
    }
}
