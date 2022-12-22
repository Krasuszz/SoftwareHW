package com.example.softwarehw.bean;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 聊天数据
 */
public class UserDataBean {
    // 用户id
    private String user_id;
    // 密码
    private String user_password;
    // 用户名
    private String user_name;
    // 是否已在线
    private boolean online;

    public UserDataBean() {
        super();
    }

    public UserDataBean(String user_id, String user_password, String user_name)
    {
        this.user_id = user_id;
        this.user_password = user_password;
        this.user_name = user_name;
        this.online = true;
    }

    public boolean is_online() {
        return online;
    }
    public void set_online() {
        this.online = true;
    }
    public void set_offline() {
        this.online = false;
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

}
