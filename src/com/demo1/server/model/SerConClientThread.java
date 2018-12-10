package com.demo1.server.model;

import com.demo1.client.comman.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @program: Gobang
 * @Date: 2018-12-10 21:24
 * @Author: long
 * @Description: 服务器到客户端的通信线程
 */
public class SerConClientThread extends Thread{
    private Socket s;

    public SerConClientThread(Socket s) {
        this.s = s;
    }

    public Socket getS() {
        return s;
    }

    @Override
    public void run() {
        while (true){
            //不断的读取从客户端那边发过来的消息
            try {
                if(!s.isClosed()) {
                    ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                    Message m = (Message) ois.readObject();

                    //对从客户端取得消息进行类型判断，然后做相应的处理
                    //稍后再做
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
