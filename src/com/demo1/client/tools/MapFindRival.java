package com.demo1.client.tools;

import com.demo1.client.comman.User;
import com.demo1.client.view.FindRival;

import java.util.HashMap;
import java.util.List;

/**
 * @program: Gobang
 * @Date: 2018-12-12 22:20
 * @Author: long
 * @Description:
 */
public class MapFindRival {
    private static HashMap<String, FindRival> hm = new HashMap<>();

    public static void addFindRival(String userName, FindRival fr){
        hm.put(userName, fr);
    }

    public static FindRival getFindRival(String userName){
        return (FindRival)hm.get(userName);
    }
}
