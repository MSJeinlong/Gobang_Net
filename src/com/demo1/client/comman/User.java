package com.demo1.client.comman;

public class User implements java.io.Serializable{
    public static final int OUT_LINE = 0;       //离线
    public static final int WAIT_VERSUS = 1;    //在线且未在对弈
    public static final int VERSUSING = 2;      //在线对弈中
    public static final int STAND_ALONE = 3;    //和电脑玩（单机）
    private int id;              //标识用户的id号
    private String name;         //账号名
    private String password;     //密码
    private String sex;          //性别
    private int dan = 1;        //段位，共九个段位，最低为初段（1段），最高为9段，默认为初段
    private int grade = 1;       //等级，每个段位10个等级，最高为10级，最低为1级，默认为1级
    private int status;          //登录状态，0为离线，1为在线未对战，2为在线对弈，

    public User() {
    }

    public User(String name, String password, String sex) {
        this.name = name;
        this.password = password;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getDan() {
        return dan;
    }

    public void setDan(int dan) {
        this.dan = dan;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    //玩家段位上升
    /**
     * @param num 上升num个段位
     **/
    public void upgrade(int num){
        grade += num;
        //升级后段位大于10，等级+1
        if(grade > 10){
            dan++;
            if(dan > 9){
                dan = 9;    //9为最高等级
            }
            grade = grade % 10; //重新调整段位
        }
    }

    //玩家段位下降，下降了n个段位
    public void degrade(int num){
        grade -= num;
        if(grade < 1){
            dan--;
            if(dan < 1){    //1-1是最低的段位了
                dan = 1;
                grade = 1;
            } else {
                if(grade == 0){  // 即n-1变为 (n-1)-9
                    grade = 9;
                } else if(grade == -1){     //即n-1变为 (n-1)-8
                    grade = 8;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", dan=" + dan +
                ", grade=" + grade +
                ", status=" + status +
                '}';
    }
}
