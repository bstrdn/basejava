package com.twodonik.webapp;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MainConcurrency {
    private static int count = 0;
    private static final Object LOCK = new Object();
    private static final AtomicInteger integer = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {


        System.out.println(Thread.currentThread().getName());


        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName());
                System.out.println(getState());
            }
        };
        thread0.start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getState());
        }).start();

        System.out.println(thread0.getState());
        ExecutorService service = Executors.newCachedThreadPool();
//List<Thread> list = new ArrayList<>();
        int count = 10000;
        CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            service.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    cccc();
                }
            latch.countDown();
            });


//            Thread thread = new Thread(()-> {
//                for (int j = 0; j < 100; j++) {
//                    cccc();
//                }
//                latch.countDown();
//            });
//            thread.start();
//            list.add(thread);

        }

//        list.forEach(thread-> {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });

        latch.await(2, TimeUnit.SECONDS);


        Thread.sleep(1000);
        System.out.println(integer.get());
service.shutdown();

    }

    private static void cccc() {
        synchronized (LOCK) {

            integer.incrementAndGet();
//            count++;
        }
    }
}
