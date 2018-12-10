package com.demo1.client.comman;

/**
 * @program: Gobang
 * @Date: 2018-12-10 20:14
 * @Author: long
 * @Description:消息类型的接口
 */
public interface MessageType {
    public final static int LOGIN_SUCCEED = 1;   //表示登录成功
    public final static int LOGIN_FAIL = 2;      //表示登录失败
    public final static int User_SignUp = 3;      //表示用户要注册
    public final static int COMMON_MESSAGE = 4;   //普通消息包
    public final static int REQUEST_ONLINE_USER = 5;    //要求得到在线的好友的请求包
    public final static int RESPONSE_ONLINE_USER = 6;    //表示这是在线好友的回复包
    public final static int User_Login = 7;        //表示用户要登录
    public final static int SignUp_SUCCEED = 8;     //注册成功
    public final static int SignUp_FAIL = 9;        //注册失败
}
