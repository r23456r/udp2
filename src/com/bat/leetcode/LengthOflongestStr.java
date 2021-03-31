package com.bat.leetcode;

import java.util.*;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2020/9/14 10:29
 **/
public class LengthOflongestStr {
    public static void main(String[] args) {
        String str = "abbcdabcdecbb123456";
        System.out.println(str.charAt(1)+""+(int)str.charAt(1));
        int max = 0;
        for (int i = 0; i < str.length(); i++) {
            if (len(str.substring(i)) > max) {
                max = len(str.substring(i));
            }
        }
        System.out.println(max);
    }

    public static Integer len(String str) {
        Set<Character> set = new LinkedHashSet<>();
        for (char c : str.toCharArray()) {
            if (!set.contains(c)) {
                set.add(c);
            } else {
                return set.size();
            }
        }
        return str.length();
    }
}
