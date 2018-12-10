package com.demo1.server.model;

import com.demo1.client.comman.Message;
import com.demo1.client.comman.MessageType;
import com.demo1.client.comman.User;
import com.demo1.client.model.UserDAO;
import com.demo1.client.model.UserDAOImpl;
import com.demo1.client.tools.MapClientConServerThread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @program: Gobang
 * @Date: 2018-12-08 09:55
 * @Author: long
 * @Description:
 * 游戏服务器
 */
public class MyServer extends Thread{
    private Socket s;
    private boolean stop = false;

    public MyServer() {
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public boolean isStop() {
        return stop;
    }

    //关闭服务器上所有的Socket
    private void closeAllSocket(){
        Set<Map.Entry<String, SerConClientThread>> entrySet  = MapSerConClientThread.getHm().entrySet();
        for(Map.Entry<String, SerConClientThread> entry:entrySet){
            try {
                entry.getValue().getS().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("启动了服务器，在端口8888进行监听...");
            ServerSocket ss = new ServerSocket(8888);
            //阻塞，等待连接
            while (true){
                s = ss.accept();
                //如果上面得到Socket是用于停止服务器的,则stop为true,退出while
                if(stop){
                    s.close();  //关闭客户端的Socket
                    //把所有的HashMap中所有的Socket关闭
                    this.closeAllSocket();
                    ss.close(); //关闭服务器端的ServerSocket
                    System.out.println("关闭服务器");
                    break;
                }
                //接受客户端发来的信息
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                //这是接受到（来自客户端）的信息
                Message getMess = (Message)ois.readObject();
                //从信息包里获取用户信息
                User u = getMess.getU();

                //这是要发送的信息
                Message sendMess = new Message();
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                System.out.println("服务器接受到的用户 name:"+u.getName()+" password:"+u.getPassword());

                //数据库操作
                UserDAO uDAO = new UserDAOImpl();
                //如果是用户登录
                if(getMess.getMesType() == MessageType.User_Login) {
                    u = uDAO.Query(getMess.getU());
                    sendMess.setU(u);
                    //验证用户是否可以合法登录
                    if (u != null) {
                        //合法用户设置消息类型为1
                        sendMess.setMesType(MessageType.LOGIN_SUCCEED);
                        //返回登录成功的消息包
                        oos.writeObject(sendMess);

                        //单开一个线程，让该线程与客户端保存通信
                        SerConClientThread scct = new SerConClientThread(s);
                        //保存客户端线程到 Map，便于后面的通信
                        MapSerConClientThread.addSerConClientThread(u.getName(), scct);
                        //启动线程
                        scct.start();
                    } else {
                        //非法用户设置消息类型为2
                        sendMess.setMesType(MessageType.LOGIN_FAIL);
                        //返回登录失败的消息包
                        oos.writeObject(sendMess);
                        //关闭连接
                        s.close();
                    }
                }
                //如果是用户注册
                else if(getMess.getMesType() == MessageType.User_SignUp){
                    //添加用户成功，则表示用户注册成功
                    if(uDAO.add(u)){
                        //设置消息类型为注册成功
                        sendMess.setMesType(MessageType.SignUp_SUCCEED);
                        oos.writeObject(sendMess);
                    } else {    //注册失败
                        //设置注册失败消息类型
                        sendMess.setMesType(MessageType.SignUp_FAIL);
                        oos.writeObject(sendMess);
                    }
                    //关闭连接
                    s.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
