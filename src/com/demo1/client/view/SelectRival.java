package com.demo1.client.view;

import com.demo1.client.comman.Message;
import com.demo1.client.comman.MessageType;
import com.demo1.client.comman.User;
import com.demo1.client.tools.MapClientConServerThread;
import com.demo1.client.tools.MapPPMainBoard;
import com.demo1.client.tools.MapUserModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;

public class SelectRival extends JDialog implements ActionListener {

    private JButton jb1, jb2, jb3;
    private String userName;

    public SelectRival(String userName) {

        this.userName = userName;
        jb1 = new JButton("人人对战");
        jb2 = new JButton("人机对战");
        jb3 = new JButton("上一级菜单");

        this.setLayout(new GridLayout(3, 1));

        jb1.addActionListener(this);
        jb2.addActionListener(this);
        jb3.addActionListener(this);
        this.add(jb1);
        this.add(jb2);
        this.add(jb3);

        this.setTitle("选择对战模式");
        this.setBounds(800, 400, 200, 150);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        User u = MapUserModel.getUser(userName);
        //判断用户选择了哪种模式
        if(e.getSource() == jb1){
            //人人模式
            dispose();
            //设置User的status 为 1， 1 表示等待对战状态
            u.setStatus(User.WAIT_VERSUS);
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
            //生成人人对战界面
            PPMainBoard ppmb = new PPMainBoard(userName);
            //把userName的PPMainBoard添加到Map，用于通信
            MapPPMainBoard.addPPMainBoard(userName, ppmb);
        } else if(e.getSource() == jb2){
            //人机对战模式
            dispose();
            new SelectLevel(userName);      //进入电脑水平模式选择
        } else if(e.getSource() == jb3){
            //返回上一级菜单
            dispose();
            new SelectModel(userName);
        }
    }
}
