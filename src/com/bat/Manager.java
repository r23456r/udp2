package com.bat;

import java.util.concurrent.CountDownLatch;

public class Manager {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        MyDevTeam teamDevA = new MyDevTeam(countDownLatch, "devA");
        MyDevTeam teamDevB = new MyDevTeam(countDownLatch, "devB");
        teamDevA.start();
        teamDevB.start();
        countDownLatch.await();
        MyQATeam qa = new MyQATeam();
        qa.start();
    }
}

class MyDevTeam extends Thread {
    CountDownLatch countDownLatch;

    public MyDevTeam(CountDownLatch countDownLatch, String name) {
        super(name);
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("Task assigned to development team " + Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Task finished by development team Thread.currentThread().getName()");
        this.countDownLatch.countDown();
    }
}

class MyQATeam extends Thread {
    @Override
    public void run() {
        System.out.println("Task assigned to QA team");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Task finished by QA team");
    }
}