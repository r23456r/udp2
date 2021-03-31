package com.bat.leetcode;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2020/9/14 14:40 Iphone11 照片2：57
 **/
public class LengthOflongestStr2Up {
    public static void main(String[] args) {
        String str = "abcabcdd";
        int[] buffer = new int[128];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = -1;
        }

        int n = str.length();
        int start = 0;
        int res = 0;
        for (int i = 0; i < n; i++) {
            //拿当前字符的ascII码
            int index = str.charAt(i);
            //开始字符的下标根据是否存在索引变化，
            // 有索引，加上当前字符就重复了，所以提取当前字符的上个索引位置的下标，作为start，逐个判断
            // 没有索引，start不变，更新默认为-1的索引为当前下标
            start = Math.max(start, buffer[index] + 1);
            //首-尾+1是当前链的长度
            res = Math.max(res, i - start + 1);
            buffer[index] = i;
        }
        System.out.println(res);
    }
}
