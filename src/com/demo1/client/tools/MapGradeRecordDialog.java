package com.demo1.client.tools;

import com.demo1.client.view.GradeRecordDialog;

import java.util.HashMap;

/**
 * @program: Gobang
 * @Date: 2018-12-11 13:52
 * @Author: long
 * @Description:
 */
public class MapGradeRecordDialog {
    private static HashMap hm = new HashMap<String, GradeRecordDialog>();

    public static void addGradeRecordDialog(String userName, GradeRecordDialog grd){
        hm.put(userName, grd);
    }

    public static GradeRecordDialog getGradeRecordDialog(String userName){
        return (GradeRecordDialog)hm.get(userName);
    }
}
