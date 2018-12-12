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
    public final static int USER_SIGN_UP = 3;      //表示用户要注册
    public final static int COMMON_MESSAGE = 4;   //普通消息包
    public final static int REQUEST_ONLINE_USER = 5;    //要求得到在线的好友的请求包
    public final static int RESPONSE_ONLINE_USER = 6;    //表示这是在线好友的回复包
    public final static int USER_LOGIN = 7;        //表示用户要登录
    public final static int SIGN_UP_SUCCEED = 8;     //注册成功
    public final static int SIGN_UP_FAIL = 9;        //注册失败
    public final static int UPDATE_GRADE = 10;       //（赢或输时）更新用户等级和保存对战记录
    public final static int RECORD_REQUEST = 11;     //用户（客户端）请求的对战记录
    public final static int RECORD_RESPONSE = 12;    //服务器返回给客户端的对战记录
    public final static int UPDATE_USER = 13;       //更新数据库里的User.status
    public final static int REQUEST_WAIT_VERSUS_USERS = 14;    //客户端请求得到所有在等待对战的用户
    public final static int RESPONSE_WAIT_VERSUS_USERS = 15;    //服务器返回所有在等待对战的用户list
}
