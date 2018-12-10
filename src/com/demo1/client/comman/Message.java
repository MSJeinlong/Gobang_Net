package com.demo1.client.comman;

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
    private String con;
    private String sendTime;
    private User u;

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

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }
}
