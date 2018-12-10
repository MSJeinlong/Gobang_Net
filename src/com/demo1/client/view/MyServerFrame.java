package com.demo1.client.view;

import com.demo1.server.model.MyServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

/**
 * @Author:long
 * @Date: 2018/12/4 21:59
 * @Version 1.0
 * 这是服务器界面，可以启动和关闭服务器，还可以管理用户
 */

public class MyServerFrame extends JFrame implements ActionListener {

    private JPanel jp1;
    private JButton jb1, jb2;
    private MyServer myServer;

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

        this.add(jp1);
        this.setTitle("游戏服务器");
        this.setBounds(500, 300, 500, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    //关闭服务器
    public void stopMyServer(){
        try {
            new Socket("localhost", 9851);
            myServer.setStop(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //启动服务器
        if(e.getSource() == jb1){
            myServer = new MyServer();
            myServer.start();
        }
        //关闭服务器
        else if(e.getSource() == jb2){
            System.out.println("点击了关闭");
            if(myServer == null) {
                JOptionPane.showMessageDialog(this, "服务器尚未启动", "错误", JOptionPane.ERROR_MESSAGE);
            } else {
                stopMyServer();
            }
        }
    }
}
