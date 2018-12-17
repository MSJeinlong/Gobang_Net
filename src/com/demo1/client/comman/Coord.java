package com.demo1.client.comman;

/**
 * 坐标的get和set方法
 *
 * @author long
 * 保存棋子的坐标
 */
public class Coord implements java.io.Serializable{
    private int x;
    private int y;
    private int role;   //棋子的类型

    public Coord() {
    }

    public Coord(int x, int y, int role) {
        this.x = x;
        this.y = y;
        this.role = role;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
