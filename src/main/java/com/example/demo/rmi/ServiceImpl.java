package com.example.demo.rmi;

import com.example.demo.rmi.service.IService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @Author xcc
 * @Date 2019/7/30 15:12
 * @des 类说明
 */
public class ServiceImpl extends UnicastRemoteObject implements IService {
    @Override
    public String queryName(String no) throws RemoteException {
        return "welcome to "+no;
    }

    protected ServiceImpl() throws RemoteException {
        super();
    }


}
