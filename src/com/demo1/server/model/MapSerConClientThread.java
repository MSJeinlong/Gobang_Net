package com.demo1.server.model;

import java.util.HashMap;

/**
 * @program: Gobang
 * @Date: 2018-12-10 21:32
 * @Author: long
 * @Description:
 */
public class MapSerConClientThread {

    private static HashMap hm = new HashMap<String, SerConClientThread>();

    public static HashMap getHm() {
        return hm;
    }

    //向hm中添加一个通信线程
    public static void addSerConClientThread(String userName, SerConClientThread scct){
        hm.put(userName, scct);
    }

    //根据userName取出相应的客户端线程
    public static SerConClientThread getClientThread(String userName){
        return (SerConClientThread)hm.get(userName);
    }

}
