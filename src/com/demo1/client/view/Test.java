package com.demo1.client.view;

import java.util.HashMap;

/**
 * @program: Gobang
 * @Date: 2018-12-12 15:28
 * @Author: long
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        HashMap<String, Integer> hm = new HashMap<>();
        hm.put("1", 2);
        hm.put("1", 3);
        hm.put("1", 5);
        System.out.println(hm.get("1"));
    }
}
