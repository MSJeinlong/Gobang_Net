package com.demo1.client.model;

import com.demo1.client.comman.GradeRecord;

import java.util.List;

public interface GradeRDAO {
    public List<GradeRecord> Query(String userName);       //根据userName查询对战成绩
    public boolean add(GradeRecord gh);
}
