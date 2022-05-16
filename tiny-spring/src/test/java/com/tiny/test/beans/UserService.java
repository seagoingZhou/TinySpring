package com.tiny.test.beans;

import org.junit.Test;

public class UserService {

    private String uid;

    private UserDAO userDAO;

    public void queryUserInfo(){
        System.out.println("查询用户信息: "+userDAO.queryUserName(uid));
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

}
