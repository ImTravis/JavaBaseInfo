package com.example.demo.ReadWriteLockDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明
 */
@RestController
@RequestMapping("lock")
public class StudentController {

    public static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    public static Lock readLock = readWriteLock.readLock();
    public static Lock writeLock = readWriteLock.writeLock();

    @Autowired
    private StudentService studentService;

    @GetMapping("getNum")
    public void getStuNum(){
        studentService.getStu(readLock);
    }

    @GetMapping("addNum")
    public void getStuNum(int num){
        studentService.incrementStu(num,writeLock);
    }
}
