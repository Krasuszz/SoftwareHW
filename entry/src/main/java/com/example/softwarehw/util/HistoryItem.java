package com.example.softwarehw.util;

public class HistoryItem {
    private String name;
    private String info;
    private String message;
    public HistoryItem(String name,String info, String content)
    {
        this.name = name;
        this.info = info;
        this.message = content;
    }
    public String getName() {
        return name;
    }
    public String getInfo() {
        return info;
    }
    public String getMessage() {
        return message;
    }
    public void setName(String name) {
        this.name = name;
    }
}
