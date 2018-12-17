package com.demo1.client.tools;

import com.demo1.client.view.PPMainBoard;

import java.util.HashMap;

/**
 * @program: Gobang
 * @Date: 2018-12-12 17:27
 * @Author: long
 * @Description:管理人人对战用户的界面的Map映射
 */
public class MapPPMainBoard {
    private static HashMap<String, PPMainBoard> hm = new HashMap<>();

    // K-V(userName-PPMainBoard)加入HashMap
    public static void addPPMainBoard(String userName, PPMainBoard ppmb){
        hm.put(userName, ppmb);
    }

    public static PPMainBoard getPPMainBoard(String userName){
        return (PPMainBoard)hm.get(userName);
    }
}
