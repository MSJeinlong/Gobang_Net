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
    private Message sendMess;   //要发送的消息包
    private Message getMess;    //得到的消息包

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
                    getMess = (Message) ois.readObject();

                    //对从客户端取得消息进行类型判断，然后做相应的处理
                    switch (getMess.getMesType()) {
                        //客户端请求保存对战记录，并更新玩家等级
                        case MessageType.UPDATE_GRADE:
                            uDAO = new UserDAOImpl();
                            uDAO.update(getMess.getU());     //用户等级信息更新到数据库
                            //保存用户战绩到数据库
                            ghDAO = new GradeRDAOImpl();
                            ghDAO.add(getMess.getGr());
                            break;
                        //客户端请求得到用户的对战记录
                        case MessageType.RECORD_REQUEST:
                            sendMess = new Message();
                            //设置消息包类型，表示这是服务器返回的
                            sendMess.setMesType(MessageType.RECORD_RESPONSE);
                            sendMess.setU(getMess.getU());
                            ghDAO = new GradeRDAOImpl();
                            //得到对应用户的对战记录
                            sendMess.setGrlist(ghDAO.Query(getMess.getU().getName()));
                            //返回消息包给客户端
                            oos = new ObjectOutputStream(s.getOutputStream());
                            oos.writeObject(sendMess);
                            System.out.println("返回 "+getMess.getU().getName()+" 的对战记录包");
                            break;
                            //客户端请求更新数据库里User信息
                        case MessageType.UPDATE_USER:
                            uDAO = new UserDAOImpl();
                            uDAO.update(getMess.getU());
                            break;
                            //服务器相应客户端请求得到的等待对战的用户列表
                        case MessageType.REQUEST_WAIT_VERSUS_USERS:
                            uDAO = new UserDAOImpl();
                            sendMess = new Message();
                            System.out.println("Server:"+getMess.getU());
                            sendMess.setU(getMess.getU());
                            //到数据库里查询等待对战的所有用户
                            sendMess.setUserList(uDAO.QueryAllWaitVersusUser());
                            //设置信息包类型
                            sendMess.setMesType(MessageType.RESPONSE_WAIT_VERSUS_USERS);
                            //返回消息包
                            oos = new ObjectOutputStream(s.getOutputStream());
                            oos.writeObject(sendMess);
                            uDAO.QueryAllWaitVersusUser();
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
