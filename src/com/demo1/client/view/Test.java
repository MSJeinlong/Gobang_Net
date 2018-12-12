package com.demo1.client.view;

import com.demo1.client.comman.User;
import com.demo1.client.model.UserDAO;
import com.demo1.client.model.UserDAOImpl;

import java.util.HashMap;
import java.util.List;

/**
 * @program: Gobang
 * @Date: 2018-12-12 15:28
 * @Author: long
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
       /* HashMap<String, Integer> hm = new HashMap<>();
        hm.put("1", 2);
        hm.put("1", 3);
        hm.put("1", 5);
        System.out.println(hm.get("1"));*/
        UserDAO userDAO = new UserDAOImpl();
        List<User> list = userDAO.QueryAllWaitVersusUser();
        for (User u:list){
         System.out.println(u);
        }
    }
}
