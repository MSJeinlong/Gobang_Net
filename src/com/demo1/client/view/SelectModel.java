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

public class SelectModel extends JFrame implements ActionListener {

    private JButton jb1, jb2;
    private String userName;

    public SelectModel(String userName) {
        this.userName = userName;
        jb1 = new JButton("对弈模式");
        jb2 = new JButton("训练模式");

        this.setLayout(new GridLayout(2, 1));

        jb1.addActionListener(this);
        jb2.addActionListener(this);
        this.add(jb1);
        this.add(jb2);

        this.setTitle("模式选择");
        this.setBounds(800, 400, 200, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //用户选择了对弈模式
        if(e.getSource() == jb1){
            dispose();
            MapUserModel.addModel(userName, MapUserModel.VERSUS);//设置模式为对弈
            new SelectRival(userName);          //进入对手选择模式
        } else if(e.getSource() == jb2){
            //用户选择了训练模式，直接进入人机训练
            dispose();
            MapUserModel.addModel(userName, MapUserModel.TRAIN);      //设置模式为训练
            new SelectLevel(userName);          //进入电脑等级选择
        }
    }
}
