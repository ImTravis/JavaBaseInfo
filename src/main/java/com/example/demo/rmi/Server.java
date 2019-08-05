package com.example.demo.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @Author xcc
 * @Date 2019/7/30 15:15
 * @des 类说明
 */
public class Server {
    public static void main(String[] args) {
        // 注册管理器
        Registry registry = null;
        try {
            // 创建一个服务注册管理器
            registry = LocateRegistry.createRegistry(8001);

        } catch (RemoteException e) {

        }
        try {
            // 创建一个服务
            ServiceImpl server = new ServiceImpl();
            // 将服务绑定命名
            registry.rebind("vince", server);

            System.out.println("bind server");
        } catch (RemoteException e) {

        }
    }

}
