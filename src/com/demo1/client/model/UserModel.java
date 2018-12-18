package com.demo1.client.model;

import com.demo1.client.comman.User;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Vector;

/**
 * @program: Gobang
 * @Date: 2018-12-18 15:54
 * @Author: long
 * @Description:
 */
public class UserModel extends AbstractTableModel {

    private Vector<String> columns;
    private Vector<Vector> rows;
    private UserModel um;

    public UserModel() {
        columns = new Vector<>();
        columns.add("ID");
        columns.add("账号");
        columns.add("性别");
        columns.add("等级");
        columns.add("状态");
        columns.add("上线时间");

        rows = new Vector<>();
        //查询所有在线的用户
        List<User> list = new UserDAOImpl().queryAllOnLineUser();
        for (User u : list) {
            Vector tmp = new Vector();
            tmp.add(u.getId());
            tmp.add(u.getName());
            tmp.add(u.getSex());
            tmp.add(u.getDan() + "-" + u.getGrade());
            switch (u.getStatus()) {
                case User.STAND_ALONE:
                    tmp.add("人机对战");
                    break;
                case User.VERSUSING:
                    tmp.add("人人对战中");
                    break;
                case User.WAIT_VERSUS:
                    tmp.add("人人待战");
                    break;

            }
            tmp.add(u.getLoginTime());
            rows.add(tmp);
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
    public String getColumnName(int column) {
        return this.columns.get(column).toString();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((Vector) rows.get(rowIndex)).get(columnIndex);
    }
}
