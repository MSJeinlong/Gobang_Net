package com.demo1.client.model;

import com.demo1.client.comman.GradeRecord;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Vector;

public class GradeRModel extends AbstractTableModel {

    Vector<String> columns;
    Vector<Vector> rows;
    GradeRDAO grDAO;

    public GradeRModel(String userName) {
        grDAO = new GradeRDAOImpl();
        columns = new Vector<String>();
        columns.add("对手");
        columns.add("胜负情况");
        columns.add("回合数");
        columns.add("对方等级");
        columns.add("我方等级");
        columns.add("时间");

        rows = new Vector();
        List<GradeRecord> list = grDAO.Query(userName);
        this.addRows(list);
    }

    private void addRows(List<GradeRecord> list){
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

    @Override
    public int getRowCount() {
        return this.rows.size();
    }

    @Override
    public int getColumnCount() {
        return this.columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((Vector) rows.get(rowIndex)).get(columnIndex);
    }
}
