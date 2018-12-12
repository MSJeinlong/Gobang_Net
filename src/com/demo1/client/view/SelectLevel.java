package com.demo1.client.view;

import com.demo1.client.comman.Message;
import com.demo1.client.comman.MessageType;
import com.demo1.client.comman.User;
import com.demo1.client.tools.MapClientConServerThread;
import com.demo1.client.tools.MapUserModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;

public class SelectLevel extends JDialog implements ActionListener {
    public static final int PRIMARY = 1; //初级
    public static final int MEDIUM = 2;  //中级
    public static final int SENIOR = 3;  //高级
    private JButton jb1, jb2, jb3, jb4;
    private String userName;

    public SelectLevel(String userName) {
        this.userName = userName;
        jb1 = new JButton("初级");
        jb2 = new JButton("中级");
        jb3 = new JButton("高级");
        jb4 = new JButton("上一步");

        this.setLayout(new GridLayout(4, 1));

        //注册监听
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        jb3.addActionListener(this);
        jb4.addActionListener(this);

        //添加组件
        this.add(jb1);
        this.add(jb2);
        this.add(jb3);
        this.add(jb4);

        //设置属性
        this.setTitle("请选择电脑等级");
        this.setBounds(800, 400, 200, 250);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        User u = MapUserModel.getUser(userName);
        if(e.getSource() == jb1){           //用户选择了电脑初级
            dispose();      //关闭当前界面
            new PCMainBoard(PRIMARY, userName);   //加载电脑初级游戏界面
        } else if(e.getSource() == jb2){    //用户选择了电脑中级
            dispose();      //关闭当前界面
            new PCMainBoard(MEDIUM, userName);   //加载电脑中级游戏界面
        } else if(e.getSource() == jb3){    //用户选择了电脑高级
            dispose();      //关闭当前界面
            new PCMainBoard(SENIOR, userName);   //加载电脑高级游戏界面
        } else if(e.getSource() == jb4){
            dispose();
            new SelectModel(userName);          //直接回到SelectModel
        }
        if(e.getSource() != jb4){
            //设置User的status 为 STAND_ALONE， STAND_ALONE 表示和电脑对战
            u.setStatus(User.STAND_ALONE);
            //关闭当前界面
            this.dispose();
            //更新Map里User
            MapUserModel.addUser(userName, u);
            //向服务器发出请求，要求更新数据库里的user.status
            try {
                //获取客户端到服务器的通信线程
                ObjectOutputStream oos = new ObjectOutputStream
                        (MapClientConServerThread.getClientConnServerThread(u.getName()).getS().getOutputStream());
                Message m = new Message();
                m.setMesType(MessageType.UPDATE_USER);
                m.setU(u);
                //通过对象流向服务器发送消息包
                oos.writeObject(m);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
