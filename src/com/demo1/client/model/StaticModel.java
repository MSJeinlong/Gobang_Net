package com.demo1.client.model;

import com.demo1.client.comman.User;

/**
 * 保存静态(游戏过程中不会变的信息)的数据模型
 * */
public class StaticModel {

    public static final int VERSUS = 1;     //对战模式
    public static final int TRAIN = 0;      //训练模式
    private static int model;    //用户选择的模式

    private static User u;      //登录游戏的用户

    public static User getU() {
        return u;
    }

    public static void setU(User u) {
        StaticModel.u = u;
    }

    public static int getModel() {
        return model;
    }

    public static void setModel(int model) {
        StaticModel.model = model;
    }

}
