package com.bat.leetcode;

import java.util.Arrays;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2020/9/14 15:02
 **/
public class FindMedianSortedArrays {
    public static double findMedianSortedArrays(int[] arr1, int[] arr2) {
        int size = arr1.length + arr2.length;
        int[] arr = new int[size];
        int x = 0;
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr2.length; j++) {
                if (arr1[i] > arr2[j]) {
                    arr[x++] = arr2[j];
                    if (i == arr1.length - 1) {
                        System.out.println(Arrays.toString(Arrays.copyOf(arr, arr1.length + Arrays.copyOfRange(arr2, j, arr2.length - 1).length)));
                    }
                } else {
                    arr[x++] = arr1[i];
                    break;
                }
            }

        }

        System.out.println(Arrays.toString(arr));
        return 0D;
    }

    public static void main(String[] args) {
        int[] ints = {1, 2, 3};
        int[] ints1 = {2, 3, 5};
        findMedianSortedArrays(ints, ints1);
        int[] copy = Arrays.copyOf(ints, ints.length + ints1.length);
        System.arraycopy(ints1, 0, copy, ints.length, ints1.length);
        Arrays.sort(copy);
        System.out.println(Arrays.toString(copy));
        for (int i = 0; i < copy.length; i++) {
            if (copy.length % 2 == 0) {
                int i1 = copy[i / 2] + copy[i / 2 + 1];
                float v = (float) i1 / 2;
                System.out.println(v);
                return;
            }
        }

    }

}
