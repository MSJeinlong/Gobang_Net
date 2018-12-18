package com.demo1.server.model;

import com.demo1.client.model.UserModel;

import javax.swing.*;
import java.awt.*;

/**
 * @program: Gobang
 * @Date: 2018-12-18 16:52
 * @Author: long
 * @Description:时刻刷新显示在线用户的面板
 */
public class UserPanel extends JPanel implements Runnable{

    private JTable jtb;
    private JScrollPane jsp;
    private UserModel um;
    private JLabel jlb_userCount;
    private JPanel infoP;
    private JPanel jp1;
    private JLabel jlb_tip;

    public UserPanel() {
        um = new UserModel();
        jtb = new JTable(um);
        jtb.setToolTipText("用户管理");
        jsp = new JScrollPane(jtb);
        jlb_userCount = new JLabel("目前共有 "+um.getRowCount()+" 个在线用户");
        infoP = new JPanel();
        infoP.add(jlb_userCount);
        jp1 = new JPanel();
        jlb_tip = new JLabel("已登录的用户列表");
        jp1.add(jlb_tip);

        this.setLayout(new BorderLayout());
        this.add(jp1, "North");
        this.add(jsp, "Center");
        this.add(infoP, "South");
    }

    @Override
    public void run() {
        while (true){
            try {
                //每半秒刷新一次
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            um = new UserModel();
            jtb.setModel(um);
            jlb_userCount.setText("目前共有 "+um.getRowCount()+" 个在线用户");
        }
    }
}
