package com.demo1.client.model;
import com.demo1.client.comman.User;
import com.demo1.client.tools.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private String sql;

    //查询
    @Override
    public User Query(User u) {
        sql = "select * from users where name = ? and password = ?";
        conn =  DBConnection.getConnection();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, u.getName());
            pstmt.setString(2, u.getPassword());
            rs = pstmt.executeQuery();
            if(rs.next()){
                //数据中存在用户u,完善u的信息即可
                u.setId(rs.getInt(1));
                u.setSex(rs.getString(4));
                u.setDan(rs.getInt(5));
                u.setGrade(rs.getInt(6));
                u.setStatus(rs.getInt(7));
                return u;
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBConnection.free(conn, pstmt, rs);
        }
        return null;
    }

    //添加
    @Override
    public boolean add(User u) {
        sql = "insert into users(name, password, sex, dan, grade, status) value(?, ?, ?, ?, ?, ?)";
        conn = DBConnection.getConnection();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, u.getName());
            pstmt.setString(2, u.getPassword());
            pstmt.setString(3, u.getSex());
            pstmt.setInt(4, u.getDan());
            pstmt.setInt(5, u.getGrade());
            pstmt.setInt(6, u.getStatus());
            if(pstmt.executeUpdate() != 0){
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBConnection.free(conn, pstmt, rs);
        }
        return false;
    }

    //删除
    @Override
    public boolean delete(int id) {
        sql = "delete from users where id = "+id;
        conn = DBConnection.getConnection();
        try {
            stmt = conn.createStatement();
            if(stmt.executeUpdate(sql) != 0){
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBConnection.free(conn, pstmt, rs);
        }
        return false;
    }

    //更新
    @Override
    public boolean update(User u) {
        sql = "update users set name = ?, password = ? , sex = ?, dan = ?, grade = ?, status = ? where id = ?";
        conn = DBConnection.getConnection();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, u.getName());
            pstmt.setString(2, u.getPassword());
            pstmt.setString(3, u.getSex());
            pstmt.setInt(4, u.getDan());
            pstmt.setInt(5, u.getGrade());
            pstmt.setInt(6, u.getStatus());
            pstmt.setInt(7, u.getId());
            if(pstmt.executeUpdate() != 0){
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBConnection.free(conn, pstmt, rs);
        }
        return false;
    }

    //查询所有状态为1的玩家
    @Override
    public List<User> QueryAllWaitVersusUser() {
        List<User> list = new ArrayList<>();
        String sql = "select * from users where status = 1";
        conn = DBConnection.getConnection();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                //数据中存在用户u,完善u的信息即可
                User u = new User();
                u.setId(rs.getInt(1));
                u.setName(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setSex(rs.getString(4));
                u.setDan(rs.getInt(5));
                u.setGrade(rs.getInt(6));
                u.setStatus(rs.getInt(7));
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.free(conn, stmt, rs);
        }
        return list;
    }

}

