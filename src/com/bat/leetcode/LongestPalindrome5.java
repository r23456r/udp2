package com.bat.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2020/9/16 9:11
 **/
public class LongestPalindrome5 {
    public static String longestPalindrome(String s) {
        char[] chars = s.toCharArray();
        int max = 0;
        String re = "";
        for (int i = 0; i < chars.length; i++) {
            for (int j = i; j < chars.length; j++) {
                System.out.println(i + "----" + (j + i));
                String substring = s.substring(i, j + 1);
                System.out.println(substring);
                if (substring.equals(reverse(substring))) {
                    int count = substring.length();
                    if (count > max) {
                        max = count;
                        re = substring;
                    }
                }
            }
        }
        return re;
    }


    public static void main(String[] args) {
//        longestPalindrome("abaabcd");
        System.out.println(longestPalindrome("acbfbcfc"));
    }

    public static String reverse(String string) {
        int length = string.length();
        char[] chars = string.toCharArray();
        if (length == 1) {
            return string;
        }

        for (int i = 0; i < chars.length / 2; i++) {
            char tmp;
            tmp = chars[chars.length - 1 - i];
            chars[chars.length - 1 - i] = chars[i];
            chars[i] = tmp;
        }
        return String.valueOf(chars);
    }
}
