package com.demo1.client.model;

import com.demo1.client.comman.Message;
import com.demo1.client.comman.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @program: Gobang
 * @Date: 2018-12-10 20:38
 * @Author: long
 * @Description:客户端和服务器通信的线程
 */
public class ClientConnServerThread extends Thread{

    private Socket s;

    public ClientConnServerThread(Socket s) {
        this.s = s;
    }

    public Socket getS() {
        return s;
    }

    @Override
    public void run() {
        while (true){
            //不停读取从服务器发来的信息
            try {
                ObjectInputStream ois  = new ObjectInputStream(s.getInputStream());
                Message m = (Message) ois.readObject();

                //判断服务器发过来的消息包的类型
                //普通的信息包
                int messType = m.getMesType();
                if(messType == MessageType.COMMON_MESSAGE){
                    //把从服务器获得消息，显示到该显示的聊天界面
                    //稍后再做
                }
                //返回在线用户的信息包

            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
