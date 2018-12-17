package com.demo1.client.view;

import com.demo1.client.comman.Message;
import com.demo1.client.comman.MessageType;
import com.demo1.client.comman.User;
import com.demo1.client.tools.MapClientConServerThread;
import com.demo1.client.tools.MapFindRival;
import com.demo1.client.tools.MapPPMainBoard;

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
public class FindRival extends JFrame implements MouseListener {

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


        jp = new JPanel();
        tip = new JLabel("双击玩家头像发起挑战");
        tip.setEnabled(false);
        jp.add(tip);
        for (JLabel jlb : onLineUser) {
            jp.add(jlb);
        }
        jsp = new JScrollPane(jp);

        toolbar = new JPanel();
        usersCount = new JLabel("");
        toolbar.add(usersCount);

        this.add(jsp, "Center");
        this.add(toolbar, "South");

        /*  this.setBounds(1000, 400, 150, 300);*/
        this.setTitle(u.getName());
        this.setLocation(1200, 400);
        /*this.pack();*/
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void showWaitUser(List<User> list) {

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.size());
            User u1 = list.get(i);
            JLabel jlb = new JLabel(i + 1 + ":" + u1.getName(), new ImageIcon("images/boy2.png"), JLabel.LEFT);
            if (u1.getName().equals(u.getName())) {
                //用户自己的头像设置不可点击
                jlb.setEnabled(false);
            }
            onLineUser.add(jlb);
        }
        jp.setLayout(new GridLayout(onLineUser.size() + 1, 1, 4, 4));
        for (JLabel jlb : onLineUser) {
            jp.add(jlb);
            jlb.addMouseListener(this);
        }
        usersCount.setText("共有 " + onLineUser.size() + " 个在线用户");
        this.pack();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //用户双击对手头像发起挑战
        if(e.getClickCount() == 2) {
            //发送挑战信息
            Message m = new Message();
            //设置消息包类型
            m.setMesType(MessageType.LAUNCH_A_CHALLENGE);
            m.setSender(u.getName());
            m.setU(u);
            //得到对手的名字
            String[] strArr = ((JLabel) e.getSource()).getText().split(":");
            //用户点击了自己头像，不能发起挑战
            if(strArr[1].equals(u.getName())){
                return;
            }
            m.setGetter(strArr[1]);
            //请求服务器转发挑战信息
            try {
                ObjectOutputStream oos = new ObjectOutputStream
                        (MapClientConServerThread.getClientConnServerThread(u.getName()).getS().getOutputStream());
                oos.writeObject(m);

                JButton jb_findRival = MapPPMainBoard.getPPMainBoard(u.getName()).getFindRival();
                jb_findRival.setText("等待对手回应");
                jb_findRival.setEnabled(false);
                this.dispose();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
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
        JLabel jlab = (JLabel) e.getSource();
        jlab.setForeground(Color.red);
        setCursor(Cursor.HAND_CURSOR);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JLabel jlab = (JLabel) e.getSource();
        jlab.setForeground(Color.black);
        setCursor(Cursor.DEFAULT_CURSOR);
    }
}
