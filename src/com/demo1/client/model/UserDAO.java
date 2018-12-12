package com.demo1.client.model;

import com.demo1.client.comman.User;

import java.util.List;

public interface UserDAO {
    public User Query(User u);
    public boolean add(User u);
    public boolean delete(int id);
    public boolean update(User u);
    public List<User> QueryAllWaitVersusUser();
}
