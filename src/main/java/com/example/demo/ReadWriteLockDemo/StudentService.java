package com.example.demo.ReadWriteLockDemo;

import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明
 */
@Service
public class StudentService {



    public static int student = 0;

    public void incrementStu(int num, Lock lock) {
        lock.lock();
        try {
            System.out.print("Writer before 学生人数为：" + student + "\n");
            student+=num;
            Thread.sleep(10000);
            System.out.print("Writer after 学生人数为：" + student + "\n");
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void getStu(Lock lock) {
        lock.lock();
        try {
            System.out.print("Read 学生人数为：" + student + "\n");

        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
