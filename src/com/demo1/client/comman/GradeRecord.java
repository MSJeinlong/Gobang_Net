package com.demo1.client.comman;
/**
 * 玩家对战成绩记录
 * 每一次定出胜负都要保存对战记录
 **/
public class GradeRecord {
    private int id;     //标识记录的id
    private String userName;    //玩家名字
    private String rivalName;   //对手名字
    private int rounds;         //对战回合数
    private String win;         //胜负情况
    private String time;        //对战时间
    private String userLevel;   //玩家等级
    private String rivalLevel;  //对手等级

    public GradeRecord() {
    }

    public GradeRecord(String userName, String rivalName, int rounds, String win, String time) {
        this.userName = userName;
        this.rivalName = rivalName;
        this.rounds = rounds;
        this.win = win;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRivalName() {
        return rivalName;
    }

    public void setRivalName(String rivalName) {
        this.rivalName = rivalName;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getRivalLevel() {
        return rivalLevel;
    }

    public void setRivalLevel(String rivalLevel) {
        this.rivalLevel = rivalLevel;
    }

    @Override
    public String toString() {
        return "GradeRecord{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", rivalName='" + rivalName + '\'' +
                ", rounds=" + rounds +
                ", win='" + win + '\'' +
                ", time='" + time + '\'' +
                ", userLevel='" + userLevel + '\'' +
                ", rivalLevel='" + rivalLevel + '\'' +
                '}';
    }
}
