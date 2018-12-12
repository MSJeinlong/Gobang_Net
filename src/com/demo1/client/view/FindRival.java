package com.demo1.client.view;

import com.demo1.client.comman.Message;
import com.demo1.client.comman.MessageType;
import com.demo1.client.comman.User;
import com.demo1.client.tools.MapClientConServerThread;
import com.demo1.client.tools.MapFindRival;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: Gobang
 * @Date: 2018-12-12 17:36
 * @Author: long
 * @Description:这是显示在线用户的Dialog，用户双击对方头像便可发起挑战
 */
public class FindRival extends JDialog implements MouseListener{

    private JPanel jp, toolbar;
    private JScrollPane jsp;
    private JLabel tip, usersCount;     //提示标签
    private ArrayList<JLabel> onLineUser;        //在线且是选择了人人对战的用户JLabel
    private User u;

    public FindRival(User u) {
        this.u = u;
        //把自个添加到Map映射里
        MapFindRival.addFindRival(u.getName(), this);
        System.out.println("FindRival加入Map");
        onLineUser = new ArrayList<>();
        //向服务器发起请求，要求添加在线等待对战的用户列表
        try {
            ObjectOutputStream oos = new ObjectOutputStream
                    (MapClientConServerThread.getClientConnServerThread(u.getName()).getS().getOutputStream());
            Message m = new Message();
            m.setMesType(MessageType.REQUEST_WAIT_VERSUS_USERS);
            m.setU(u);
            oos.writeObject(m);
        } catch (IOException e) {
            e.printStackTrace();
        }

    /*    List<User> list = MapFindRival.getUserList(u.getName());
        if(list == null){
            System.out.println("list为Null");
        }
        for (int i = 0; i < list.size(); i++) {
            JLabel jlb = new JLabel(i+1+":"+u.getName(), new ImageIcon("images/boy2.png"), JLabel.LEFT);
            jlb.addMouseListener(this);
            onLineUser.add(jlb);
        }*/

        jp = new JPanel();
        tip = new JLabel("双击玩家头像发起挑战");
        tip.setEnabled(false);
        jp.add(tip);
        for(JLabel jlb:onLineUser){
            jp.add(jlb);
        }
        jsp = new JScrollPane(jp);

        toolbar = new JPanel();
        usersCount = new JLabel("");
        toolbar.add(usersCount);

        this.add(jsp, "Center");
        this.add(toolbar, "South");
      /*  this.setBounds(1000, 400, 150, 300);*/
        this.setLocation(1200, 400);
        /*this.pack();*/
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    public void showWaitUser(List<User> list){

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.size());
            User u1 = list.get(i);
            JLabel jlb = new JLabel(i+1+":"+u1.getName(), new ImageIcon("images/boy2.png"), JLabel.LEFT);
            if(u1.getName().equals(u.getName())){
                //用户自己的头像设置不可点击
                jlb.setEnabled(false);
            }
            jlb.addMouseListener(this);
            onLineUser.add(jlb);
        }
        jp.setLayout(new GridLayout(onLineUser.size() + 1, 1, 4, 4));
        for(JLabel jlb:onLineUser){
            jp.add(jlb);
            jlb.addMouseListener(this);
        }
        usersCount.setText("共有 "+onLineUser.size()+" 个在线用户");
        this.pack();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //用户双击对手头像发起挑战
       if(e.getClickCount() == 2){

       }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel jlab = (JLabel)e.getSource();
        jlab.setForeground(Color.red);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JLabel jlab = (JLabel)e.getSource();
        jlab.setForeground(Color.black);
    }
}
