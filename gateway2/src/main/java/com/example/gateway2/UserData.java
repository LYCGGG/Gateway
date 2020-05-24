package com.example.gateway2;

//存储用户数据
public class UserData {
    private String username;
    private String userpasswd;
//    用户数据设置
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){this.username = username;}
    public String getPasswd(){return userpasswd; }
    public void setUserpasswd(String userpasswd){this.userpasswd = userpasswd;}
    public UserData(String username,String userpasswd){
        super();
        this.username = username;
        this.userpasswd = userpasswd;
    }
}
