package com.demo1.client.model;

import com.demo1.client.comman.User;

import java.util.HashMap;

/**
 * 保存静态(游戏过程中不会变的信息)的数据模型
 * */
public class MapUserModel {

    public static final int VERSUS = 1;     //对战模式
    public static final int TRAIN = 0;      //训练模式
    private static int model;    //用户选择的模式

    private static HashMap modelMap = new HashMap<String, Integer>();   //用户名和用户选择的游戏模式建立映射关系

    private static HashMap userMap = new HashMap<String, User>();   //用户名和用户信息建立映射关系

    //添加modelMap K-V
    public static void addModel(String userName, Integer model){
        modelMap.put(userName, model);
    }

    //获得modelMap V
    public static int getModel(String userName){
        return  (Integer)modelMap.get(userName);
    }

    //添加user K-V
    public static void addUser(String userName, User u){
        userMap.put(userName, u);
    }

    //获得user
    public static User getUser(String userName){
        return (User)userMap.get(userName);
    }

}
