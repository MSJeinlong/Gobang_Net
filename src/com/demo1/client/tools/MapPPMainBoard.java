package com.demo1.client.tools;

import com.demo1.client.view.PPMainBoard_Demo2;

import java.util.HashMap;

/**
 * @program: Gobang
 * @Date: 2018-12-12 17:27
 * @Author: long
 * @Description:管理人人对战用户的界面的Map映射
 */
public class MapPPMainBoard {
    private static HashMap<String, PPMainBoard_Demo2> hm = new HashMap<>();

    // K-V(userName-PPMainBoard)加入HashMap
    public static void addPPMainBoard(String userName, PPMainBoard_Demo2 ppmb){
        hm.put(userName, ppmb);
    }

    public static PPMainBoard_Demo2 getPPMainBoard(String userName){
        return (PPMainBoard_Demo2)hm.get(userName);
    }
}
