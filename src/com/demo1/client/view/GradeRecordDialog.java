package com.demo1.client.view;

import com.demo1.client.comman.GradeRecord;
import com.demo1.client.comman.Message;
import com.demo1.client.comman.MessageType;
import com.demo1.client.comman.User;
import com.demo1.client.model.GradeRDAO;
import com.demo1.client.model.GradeRDAOImpl;
import com.demo1.client.model.GradeRModel;
import com.demo1.client.tools.MapClientConServerThread;
import com.demo1.client.tools.MapGradeRecordDialog;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Vector;

/**
 * 显示玩家游戏战绩的对话框
 **/
public class GradeRecordDialog extends JDialog {
    private JTable table;
    private User u;
    private JScrollPane jsp;
    private GradeRDAO grDAO;
    private GradeRModel grModel;
    private JPanel infobar;     //用于显示记录总数的面板
    private JLabel label_count; //用于显示记录总数的标签

    public GradeRecordDialog(Frame owner, String title, User u) {
        super(owner, title);
        this.u = u;
        //把自个加入Map映射
        MapGradeRecordDialog.addGradeRecordDialog(u.getName(), this);

        grModel = new GradeRModel();
        table = new JTable(grModel);
        jsp = new JScrollPane(table);

        //南部的面板
        infobar = new JPanel();
        label_count = new JLabel();

        infobar.add(label_count);

        this.setLayout(new BorderLayout());
        this.add(jsp, "Center");
        this.add(infobar, "South");

        //设置JDialog属性
        this.setTitle(u.getName()+"的历史战绩");
        this.setBounds(500, 400, 1100, 500);
       /* this.setLocation(500, 400);
        this.pack();*/
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setVisible(true);

        //向服务器发出请求，要求得到用户的对战记录
        try {
            //获取客户端到服务器的通信线程
            ObjectOutputStream oos = new ObjectOutputStream
                    (MapClientConServerThread.getClientConnServerThread(u.getName()).getS().getOutputStream());
            Message sendMs = new Message();
            sendMs.setMesType(MessageType.RECORD_REQUEST);
            sendMs.setU(u);
            //通过对象流向服务器发送消息包
            oos.writeObject(sendMs);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //更新历史记录表
    public void updateGradeRecord(List<GradeRecord> list){
        grModel = new GradeRModel();
        grModel.updateRows(list);
        table.setModel(grModel);
        label_count.setText("共有 "+grModel.getRowCount()+" 条记录");
    }
}
