package com.demo1.client.model;

import com.demo1.client.comman.Message;
import com.demo1.client.comman.MessageType;
import com.demo1.client.comman.User;
import com.demo1.client.tools.MapClientConServerThread;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @program: Gobang
 * @Date: 2018-12-10 20:24
 * @Author: long
 * @Description:这是客户端连接到服务器的后台
 */
public class ClientConnServer {
    private Socket s;

    //连接到服务器的方法
    public boolean ConnectServer(Object o) {
        boolean b = false;

        try {
            //连接到服务器，服务器的IP地址为192.168.1.146
            s = new Socket("192.168.1.146", 8888);
            //把登录信息通过对象流发送服务器
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(o);
            //读取服务器返回的验证结果
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            Message ms = (Message) ois.readObject();

            //根据消息类型做出相应的操作
            //登录成功
            if (ms.getMesType() == MessageType.LOGIN_SUCCEED) {
                //创建一个用于客户端和服务器通信的线程
                ClientConnServerThread ccst = new ClientConnServerThread(s);
                //启动该通信线程
                ccst.start();
                //把线程添加到Map映射，
                User u = ms.getU();
                MapClientConServerThread.addClientConnServerThread(u.getName(), ccst);
                //把用户user也添加到Map映射
                MapUserModel.addUser(u.getName(), u);
                b = true;
            } else if (ms.getMesType() == MessageType.SIGN_UP_SUCCEED) {
                //注册成功
                b = true;
                s.close();
            } else if(ms.getMesType() == MessageType.SIGN_UP_FAIL || ms.getMesType() == MessageType.LOGIN_FAIL){
                //注册失败或者登录失败
                s.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    //检查登录用户是否合法
    public boolean checkUser(Message m) {
        return ConnectServer(m);
    }

    //用户进行注册，连接到服务器
    public boolean UserSignUp(Message m) {
        return ConnectServer(m);
    }
}
