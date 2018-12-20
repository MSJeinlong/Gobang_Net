package com.demo1.server.view;

import com.demo1.client.model.UserModel;
import com.demo1.server.model.MyServer;
import com.demo1.server.model.UserPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

/**
 * @program: Gobang
 * @Date: 2018-12-10 21:45
 * @Author: long
 * @Description:服务器启动与关闭的界面
 */
public class MyServerFrame extends JFrame implements ActionListener {
    private JPanel jp1, jp2;
    private JButton jb1, jb2;
    private MyServer server;
    private UserPanel up;

    public static void main(String[] args) {
        new MyServerFrame();
    }

    public MyServerFrame(){
        jp1 = new JPanel();
        jb1 = new JButton("启动服务器");
        jb1.addActionListener(this);
        jb2 = new JButton("关闭服务器");
        jb2.addActionListener(this);
        jp1.add(jb1);
        jp1.add(jb2);

        up = new UserPanel();
        Thread t = new Thread(up);
        t.start();

        this.add(jp1, "North");
        this.add(up, "Center");
        this.setTitle("游戏服务器");
        this.setBounds(500, 300, 900, 500);
       /* this.pack();*/
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    //关闭服务器的方法
    public void stopMyServer(){
        try {
            new Socket("localhost", 8888);
            server.setStop(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //启动服务器
        if(e.getSource() == jb1){
            if(server != null && !server.isStop()){
                JOptionPane.showMessageDialog(this, "服务器已经启动", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            server = new MyServer();
            server.start();
        }
        //关闭服务器
        else if(e.getSource() == jb2){
            /*System.out.println("点击了关闭");*/
            if(server == null) {
                JOptionPane.showMessageDialog(this, "服务器尚未启动", "错误", JOptionPane.ERROR_MESSAGE);
            } else if( server.isStop()){
                JOptionPane.showMessageDialog(this, "服务器已经关闭", "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                stopMyServer();
            }
        }
    }
}
