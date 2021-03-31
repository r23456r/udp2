package com.bat.current;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeDemo {

    static Unsafe unsafe;
    static long offSet;
    static long count;

    static {
        try {

            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe= (Unsafe)theUnsafe.get(null);
            offSet = unsafe.objectFieldOffset(UnsafeDemo.class.getDeclaredField("offSet"));

        }catch (Exception e){

        }
    }

    public static void main(String[] args) {
        UnsafeDemo unsafeDemo = new UnsafeDemo();
        boolean b = unsafe.compareAndSwapInt(unsafeDemo, offSet, 0, 1);
        System.out.println(b);
    }

}