package com.demo1.client.view;

import com.demo1.client.comman.GradeRecord;
import com.demo1.client.model.GradeRDAO;
import com.demo1.client.model.GradeRDAOImpl;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;

/**
 * 显示玩家游戏战绩的对话框
 **/
public class GradeRecordDialog extends JDialog {
    private JTable table;
    private String userName;
    private JScrollPane jsp;
    private JPanel jp1;
    Vector<String> columns;
    Vector<Vector> rows;
    GradeRDAO grDAO;

    public GradeRecordDialog(Frame owner, String title, String userName) {
        super(owner, title);
        this.userName = userName;


        columns = new Vector<String>();
        columns.add("对手");
        columns.add("胜负情况");
        columns.add("回合数");
        columns.add("对方等级");
        columns.add("我方等级");
        columns.add("时间");

        grDAO = new GradeRDAOImpl();
        List<GradeRecord> list= grDAO.Query(userName);
        this.addRows(list);

        table = new JTable(rows, columns);
        jsp = new JScrollPane(table);
        jp1 = new JPanel();

        this.setLayout(new BorderLayout());
        this.add(jsp);

        //设置JDialog属性
        this.setBounds(500, 400, 900, 500);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        /*this.setResizable(false);*/
    }

    private void addRows(List<GradeRecord> list){
        rows = new Vector<>();
        for(GradeRecord gr:list){
            Vector temp = new Vector();
            temp.add(gr.getRivalName());
            temp.add(gr.getWin());
            temp.add(gr.getRounds());
            temp.add(gr.getRivalLevel());
            temp.add(gr.getUserLevel());
            temp.add(gr.getTime());
            //把temp数据添加到rows
            rows.add(temp);
        }
    }
}
