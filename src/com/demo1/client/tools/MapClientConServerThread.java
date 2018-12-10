package com.demo1.client.tools;

import com.demo1.client.model.ClientConnServer;
import com.demo1.client.model.ClientConnServerThread;

import java.util.HashMap;

/**
 * @program: Gobang
 * @Date: 2018-12-10 20:58
 * @Author: long
 * @Description:
 * 将用户Id与客户端与服务器的通信线程建立Map映射，便于管理
 */
public class MapClientConServerThread {
    private static HashMap hm = new HashMap<String, ClientConnServer>();

    //把创建好的ClientConnServerThread放入的hm
    public static void addClientConnServerThread(String userName, ClientConnServerThread ccst){
        hm.put(userName, ccst);
    }

    //可以通过userName取得该线程
    public static ClientConnServerThread getClientConnServerThread(String userName){
        return (ClientConnServerThread)hm.get(userName);
    }
}
