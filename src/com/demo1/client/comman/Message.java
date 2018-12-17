package com.demo1.client.comman;

import java.util.List;

/**
 * @program: Gobang
 * @Date: 2018-12-08 10:02
 * @Author: long
 * @Description:
 */
public class Message implements java.io.Serializable{
    private int mesType;
    private String sender;
    private String getter;
    private String chatContent;     //聊天内容
    private User u;         //用户自己
    private GradeRecord gr;
    private List<GradeRecord> grlist;       //历史战绩列表
    private List<User> userList;            //用户列表
    private boolean acceptChallenge;        //接受挑战与否
    private Coord coord;                    //保存棋子的坐标
    private boolean agreedUndoChess;        //是否同意悔棋

    public Message() {
    }

    public int getMesType() {
        return mesType;
    }

    public void setMesType(int mesType) {
        this.mesType = mesType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    public GradeRecord getGr() {
        return gr;
    }

    public void setGr(GradeRecord gr) {
        this.gr = gr;
    }

    public List<GradeRecord> getGrlist() {
        return grlist;
    }

    public void setGrlist(List<GradeRecord> grlist) {
        this.grlist = grlist;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public boolean isAcceptChallenge() {
        return acceptChallenge;
    }

    public void setAcceptChallenge(boolean acceptChallenge) {
        this.acceptChallenge = acceptChallenge;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public boolean isAgreedUndoChess() {
        return agreedUndoChess;
    }

    public void setAgreedUndoChess(boolean agreedUndoChess) {
        this.agreedUndoChess = agreedUndoChess;
    }
}
