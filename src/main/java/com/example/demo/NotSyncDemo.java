package com.example.demo;

/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明
 */
public class NotSyncDemo {
    public static int i = 0;

    static class ThreadDemo extends Thread {
        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                addIncrement();
            }
        }

        public synchronized void addIncrement() {
            i++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadDemo t1 = new ThreadDemo();
        Thread t2 = new Thread(t1);
        Thread t3 = new Thread(t1);
        Thread t4 = new Thread(t1);

        t2.start();
        t3.start();
        t4.start();

        t4.join();
        t3.join();
        t2.join();
        System.out.println(i);
    }
}