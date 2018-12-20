package com.demo1.client.model;

import com.demo1.client.comman.GradeRecord;

import java.util.List;

public interface GradeRDAO {
    public List<GradeRecord> Query(String userName);       //根据userName查询对战成绩

    public boolean add(GradeRecord gh);                    //保存（添加）游戏记录

    public int getWinCount(String userName);           //得到userName的胜的次数

    public int getLoseCount(String userName);          //得到userName的负的次数

    public int getPeaceCount(String userName);          //得到userName的和棋的次数

}
