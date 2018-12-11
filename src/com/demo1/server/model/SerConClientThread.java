package com.demo1.server.model;

import com.demo1.client.comman.Message;
import com.demo1.client.comman.MessageType;
import com.demo1.client.model.GradeRDAO;
import com.demo1.client.model.GradeRDAOImpl;
import com.demo1.client.model.UserDAO;
import com.demo1.client.model.UserDAOImpl;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @program: Gobang
 * @Date: 2018-12-10 21:24
 * @Author: long
 * @Description: 服务器到客户端的通信线程
 */
public class SerConClientThread extends Thread {
    private Socket s;
    private GradeRDAO ghDAO;
    private UserDAO uDAO;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public SerConClientThread(Socket s) {
        this.s = s;
    }

    public Socket getS() {
        return s;
    }

    @Override
    public void run() {
        while (true) {
            //不断的读取从客户端那边发过来的消息
            try {
                if (!s.isClosed()) {
                    ois = new ObjectInputStream(s.getInputStream());
                    Message m = (Message) ois.readObject();

                    //对从客户端取得消息进行类型判断，然后做相应的处理
                    switch (m.getMesType()) {
                        //客户端请求保存对战记录，并更新玩家等级
                        case MessageType.UPDATE_GRADE:
                            uDAO = new UserDAOImpl();
                            uDAO.update(m.getU());     //用户等级信息更新到数据库
                            //保存用户战绩到数据库
                            ghDAO = new GradeRDAOImpl();
                            ghDAO.add(m.getGr());
                            break;
                        //客户端请求得到用户的对战记录
                        case MessageType.RECORD_REQUEST:
                            Message m2 = new Message();
                            //设置消息包类型，表示这是服务器返回的
                            m2.setMesType(MessageType.RECORD_RESPONSE);
                            m2.setU(m.getU());
                            ghDAO = new GradeRDAOImpl();
                            //得到对应用户的对战记录
                            m2.setGrlist(ghDAO.Query(m.getU().getName()));
                            //返回消息包给客户端
                            oos = new ObjectOutputStream(s.getOutputStream());
                            oos.writeObject(m2);
                            System.out.println("返回 "+m.getU().getName()+" 的对战记录包");
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
