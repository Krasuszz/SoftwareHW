package com.example.softwarehw.bean;

/**
 * 用户数据
 */
public class UserDataBean {
    // 用户id
    private String user_id;
    // 密码
    private String user_password;
    // 用户名
    private String user_name;
    // 是否登录
    private boolean is_online;

    public UserDataBean() {
        super();
    }

    public UserDataBean(String user_id, String user_password, String user_name, boolean is_online)
    {
        this.user_id = user_id;
        this.user_password = user_password;
        this.user_name = user_name;
        this.is_online = is_online;
    }


    public String get_user_id() {
        return user_id;
    }
    public String get_user_password() {
        return user_password;
    }
    public String get_user_name() {
        return user_name;
    }
    public boolean get_is_online() { return is_online;}

    public void set_user_id(String user_id) {
        this.user_id = user_id;
    }
    public void set_user_password(String user_password) {
        this.user_password=user_password;
    }
    public void set_user_name(String user_name) {
        this.user_name=user_name;
    }
    public void set_is_online(boolean is_online) { this.is_online=is_online;}

}
