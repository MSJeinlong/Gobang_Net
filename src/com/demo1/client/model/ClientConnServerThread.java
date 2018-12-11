package com.demo1.client.model;

import com.demo1.client.comman.Message;
import com.demo1.client.comman.MessageType;
import com.demo1.client.tools.MapGradeRecordDialog;

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

             /*   //普通的信息包
                int messType = m.getMesType();
                if(messType == MessageType.COMMON_MESSAGE){
                    //把从服务器获得消息，显示到该显示的聊天界面
                    //稍后再做
                }
                //返回在线用户的信息包*/

                //判断服务器发过来的消息包的类型
                switch (m.getMesType()){
                    //服务器返回用户对战记录
                    case MessageType.RECORD_RESPONSE:
                        //拿到客户端的历史记录Dialog,更新数据
                        System.out.println("这是客户端：得到并显示"+m.getU().getName()+" 对战记录");
                        MapGradeRecordDialog.getGradeRecordDialog(m.getU().getName()).updateGradeRecord(m.getGrlist());
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
