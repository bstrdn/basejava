package com.twodonik.webapp;

public class MainConcurrency {
    private static final Thread thread1 = new T1();
    private static final Thread thread2 = new T2();


    public static void main(String[] args) {
        thread1.start();
        thread2.start();

        System.out.println("End");
    }


    static synchronized void m1() throws InterruptedException {
        Thread.sleep(500);
        m2();
        System.out.println("First thread");
    }

    static synchronized void m2() throws InterruptedException {
        Thread.sleep(500);
        m1();
        System.out.println("Second thread");
    }


    private static class T1 extends Thread {
        @Override
        public void run() {
            System.out.println("Hello" + getName());
            try {
                m1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private static class T2 extends Thread {
        @Override
        public void run() {
            System.out.println("Hello" + Thread.currentThread().getName());
            try {
                m2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}