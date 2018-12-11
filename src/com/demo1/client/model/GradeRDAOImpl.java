package com.demo1.client.model;

import com.demo1.client.comman.GradeRecord;
import com.demo1.client.tools.DBConnection;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GradeRDAOImpl implements GradeRDAO {

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    @Override
    public List<GradeRecord> Query(String userName) {
        List<GradeRecord> list = new ArrayList<>();
        String sql = "select * from gradeHistory where userName = ?";
        conn = DBConnection.getConnection();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userName);
            rs = pstmt.executeQuery();
            while (rs.next()){
                GradeRecord gh = new GradeRecord();
                gh.setId(rs.getInt(1));
                gh.setUserName(rs.getString(2));
                gh.setRivalName(rs.getString(3));
                gh.setRounds(rs.getInt(4));
                gh.setWin(rs.getString(5));
                //读取datatime格式的数据
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp(6));
                gh.setTime(time);
                gh.setUserLevel(rs.getString(7));
                gh.setRivalLevel(rs.getString(8));
                gh.setModel(rs.getString(9));
                list.add(gh);
            }
            return list;
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBConnection.free(conn, pstmt, rs);
        }
        return null;
    }

    @Override
    public boolean add(GradeRecord gh) {
        String sql = "insert into gradeHistory(userName, rivalName, rounds, win, time, userLevel, rivalLevel, model) value(?, ?, ?, ?, ?, ?, ?, ?)";
        conn = DBConnection.getConnection();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, gh.getUserName());
            pstmt.setString(2, gh.getRivalName());
            pstmt.setInt(3, gh.getRounds());
            pstmt.setString(4, gh.getWin());
            //储存datetime，格式转换
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = null;
            try {
                date = dateFormat.parse(gh.getTime());
            } catch (ParseException e){
                e.printStackTrace();
            }
            Timestamp d = new Timestamp((date.getTime()));
            pstmt.setTimestamp(5, d);
            pstmt.setString(6, gh.getUserLevel());
            pstmt.setString(7, gh.getRivalLevel());
            pstmt.setString(8, gh.getModel());
            if(pstmt.executeUpdate() != -1){
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBConnection.free(conn, pstmt, rs);
        }
        return false;
    }

}

