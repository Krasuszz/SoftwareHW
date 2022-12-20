package bean;

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

    // 用户密码
    private String password;

    // 账号
    private int account;

    //用户名
    private String user_name;

    public ChatDataBean() {
        super();
    }

    public ChatDataBean(String sender,int picIndex,String content) {
        this.sender = sender;
        this.content = content;
        this.picIndex = picIndex;
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
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
}