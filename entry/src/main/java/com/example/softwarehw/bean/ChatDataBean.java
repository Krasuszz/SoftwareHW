package com.example.softwarehw.bean;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 聊天数据
 */
public class ChatDataBean {
    // 发送者
    private String sender;
    // 消息内容
    private String content;
    // 头像索引
    private int picIndex;


    // 发送用户
    private String send_account;
    // 接收用户
    private String receive_account;
    // 日期
    private String date;
    // 时间
    private String time;

    public ChatDataBean() {
        super();
    }

    public ChatDataBean(String sender, String send_account, String receive_account, String date, String time, int picIndex, String content) {
        this.sender = sender;
        this.send_account = send_account;
        this.receive_account = receive_account;
        this.date = date;
        this.time = time;
        this.content = content;
        this.picIndex = picIndex;
    }


    public String getSender() {
        return sender;
    }
    public void setSender(String Sender) {
        this.sender = Sender;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public int getPicIndex() {
        return picIndex;
    }
    public void setPicIndex(int picIndex) {
        this.picIndex = picIndex;
    }

    public String getSend_account() {
        return send_account;
    }
    public void setSend_account(String send_account) {
        this.send_account = send_account;
    }

    public String getReceive_account() {
        return receive_account;
    }
    public void setReceive_account(String receive_account) {
        this.receive_account = receive_account;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

}
